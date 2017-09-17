package modules

import Module
import client
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.LongNode
import io.getConfBranch
import io.setConfBranch
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import sx.blah.discord.handle.obj.IUser
import java.util.*

/**
 * Quick little module mimicking moobot, to get a hang of the API
 *
 * Features:
 * + f
 * + respect
 * + top
 * + moo
 * + harambe w/ status
 * . fortune - probably not coming, not without a *nix system :(
 */
class MooModule : Module() {

    override fun handleMessageReceived(e: MessageReceivedEvent) {
        val author = e.message.author


        /* Search for exact matches first */
        var msg =
                when (e.message.content.toLowerCase()) {
                    "f" -> "${increaseRespect(author)} pays their respects."
                    "respect" -> "$author has ${getRespect(author)} respect."
                    "top" -> "here's a list of the biggest nolifers\n ```${getTopRespects()}```"
                    "moo" -> "```         (__)\r\n         (oo)\r\n   /------\\/\r\n  / |    ||\r\n *  /\\---/\\\r\n    ~~   ~~\r\n....\"Have you mooed today?\"...```"
                    "harambe-status" -> harambeStatus()
                    else -> ""
                }


        /* No exact match found, search for substrings */
        if (msg == "") {
            if (e.message.content.toLowerCase().contains("harambe")) msg = harambe()
        }

        if (msg != "") e.message.channel.sendMessage(msg)

    }

    private fun harambeStatus(): String {

        val streak = getConfBranch("harambe", "streak").intValue()
        val last = getConfBranch("harambe", "last").longValue()


        /* Time, in hours, since Harambe was last mentioned  */
        val hours = (last - System.currentTimeMillis()) / 360000

        return if (streak > 0)
            "Current streak: $streak days. Harambe was last mentioned $hours hours ago."
        else
            "Harambe is forgotten :( Last mention was $hours hours ago."
    }


    /* Harambe */
    private fun harambe(): String {

        val streak = getConfBranch("harambe", "streak").intValue()

        /* Time, in hours, since Harambe was last mentioned  */
        val hours = (getConfBranch("harambe", "last").longValue() - System.currentTimeMillis()) / 360000

        setConfBranch(LongNode(System.currentTimeMillis()), "harambe", "last")

        if (hours > 48) {
            setConfBranch(IntNode(0), "harambe", "streak")
            return "Days since Harambe was last mentioned: ${hours / 24} -> 0"
        }

        if (hours > 24) {
            setConfBranch(IntNode(streak + 1), "harambe", "streak")
            return "Current Harambe daily streak: $streak"
        }

        return ""

    }

    /* Respects */
    private fun increaseRespect(author: IUser): IUser {

        var respect = 1

        val branch = getConfBranch("respect", author.longID.toString())
        if (branch is IntNode) respect = branch.intValue() + 1

        setConfBranch(IntNode(respect), "respect", author.longID.toString())

        return author
    }

    private fun getRespect(author: IUser): Int = getConfBranch("respect", author.longID.toString()).intValue()

    private fun getTopRespects(): String = /* Oh the laziness */
            getConfBranch("respect").fields()
                    .asSequence()
                    .sortedWith(Comparator.comparingInt { it.value.intValue() })
                    .toList()
                    .reversed()
                    .fold("") { str, ele ->
                        "$str\n${client.getUserByID(ele.key.toLong()).name}: ${ele.value}"
                    }


}