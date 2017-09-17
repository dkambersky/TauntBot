package modules.tournament

import Module
import com.exsoloscript.challonge.Challonge
import com.exsoloscript.challonge.model.Participant
import com.exsoloscript.challonge.model.enumeration.MatchState
import com.fasterxml.jackson.databind.node.TextNode
import io.getConfBranch
import io.setConfBranch
import sx.blah.discord.api.internal.json.objects.EmbedObject
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent

import sx.blah.discord.handle.impl.obj.Embed
import sx.blah.discord.handle.obj.IMessage
import java.awt.Color
import java.time.LocalDateTime

/**
 * Created by dkambersky on 10/04/2017
 */
class TournamentModule : Module() {

    val api = Challonge.getFor(getConfBranch("tournament", "username").textValue(), getConfBranch("tournament", "api-key").textValue())!!

    val tournament = api.tournaments().getTournament(getConfBranch("tournament", "current-url").textValue(), true, true).sync()!!

    var lastMsg: IMessage? = null

    override fun handleMessageReceived(e: MessageReceivedEvent) {
        // TODO customizable prefix

        if (e.message.content[0] == '~') {
            lastMsg = e.message
            val tokens = e.message.content.substring(1).split(' ')

            when (tokens[0]) {
                "create" -> createTournament(tokens)
                "match" -> updateMatch(tokens)
                "register" -> registerName(tokens[1], e.message.author.longID)
                "associate" -> setCurrentTournament(tokens[1])
                "tournament" -> showTourney()
            }

        }
        val matches = tournament.matches()

        e.message.channel.sendMessage("Tournament handler firing! ")


    }

    private fun showTourney() {
        // TODO show current standings
        val embed = EmbedObject(Embed("Tournament Information", "rich", "${tournament.name()}", tournament.fullChallongeUrl(), "", null, LocalDateTime.now(), Color(253, 55, 29), Embed.EmbedFooter("ChallongeModule @ KotlinBot", "http://i.imgur.com/3gxvXqe.png"), "http://i.imgur.com/3gxvXqe.png", "", Embed.EmbedAuthor("Davefin", "", ""), null))
        lastMsg!!.channel.sendMessage(getMatchups())
        lastMsg!!.channel.sendMessage("", embed, false)
        //modules.tournament.getBracket()
    }

    private fun getMatchups(): String {
        val builder = StringBuilder()
        builder.append("**Current Open Matchups:**\n")
        for (match in tournament.matches().filter { it.state() == MatchState.OPEN || it.state() == MatchState.COMPLETE }) {

            //TODO use discord names if applicable

            val player1 = getShowName(api.participants().getParticipant(tournament.url(), match.player1Id(), false).sync())
            val player2 = getShowName(api.participants().getParticipant(tournament.url(), match.player2Id(), false).sync())
            val score = match.scoresCsv()

            builder.append("**$score** | ${player1.padEnd(10, ' ')} :crossed_swords: $player2 \n")
        }
        return builder.toString()
    }


    private fun getShowName(participant: Participant): String {


        if (false) {
            // TODO discord nick integration
        }
        if (participant.challongeUsername() ?: "" != "") return participant.challongeUsername()
        if (participant.name() ?: "" != "") return participant.name()

        // Failsafe
        return participant.id().toString()
    }


    /**
     * Associates the bot with a tournament
     */
    private fun setCurrentTournament(s: String) {
        setConfBranch(TextNode(s), "tournament", "current-url")
    }


    /**
     * Associates a Discord account with a Challonge account
     */
    private fun registerName(s: String, id: Long) {
        //TODO register names
    }

    private fun updateMatch(tokens: List<String>) {

    }

    private fun createTournament(tokens: List<String>) {
        //TODO create tournaments

    }

}

