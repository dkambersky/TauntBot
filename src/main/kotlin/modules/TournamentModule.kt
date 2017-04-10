package modules

import Module
import com.exsoloscript.challonge.Challonge
import io.getConfBranch
import sx.blah.discord.handle.impl.events.MessageReceivedEvent

/**
 * Created by dkambersky on 10/04/2017
 */
class TournamentModule : Module() {

    val api = Challonge.getFor(getConfBranch("tournament", "username").textValue(), getConfBranch("tournament", "api-key").textValue())!!

    val tourneys = api.tournaments()!!


    override fun handleMessageReceived(e: MessageReceivedEvent) {
        e.message.channel.sendMessage("tourneys: " + tourneys)
    }

}

