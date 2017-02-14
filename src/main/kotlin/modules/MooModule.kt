package modules

import Module
import client
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.LongNode
import io.getConfBranch
import io.setConfBranch
import sx.blah.discord.handle.impl.events.MessageReceivedEvent
import sx.blah.discord.handle.obj.IUser
import java.util.*

/**
 * Quick little module mimicking moobot, to get a hang of the API
 *
 * Features:
 * -> f
 * -> respect
 * -> top
 * -> moo
 * -> harambe
 * -<  fortune - probably not coming, not on a *nix system
 */
class MooModule : Module() {

    override fun handleMessageReceived(e: MessageReceivedEvent) {
        val author = e.message.author


        val msg =
                when (e.message.content.toLowerCase()) {
                    "f" -> "${increaseRespect(author)} pays their respects."
                    "respect" -> "$author has ${getRespect(author)} respect."
                    "top" -> "here's a list of the biggest nolifers ```${getTopRespects()}```"
                    "moo" -> "```         (__)\r\n         (oo)\r\n   /------\\/\r\n  / |    ||\r\n *  /\\---/\\\r\n    ~~   ~~\r\n....\"Have you mooed today?\"...```"
                    "harambe" -> harambe()
                    else -> ""
                }

        if (msg != "") e.message.channel.sendMessage(msg)

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

        val branch = getConfBranch("respect", author.id)
        if (branch is IntNode) respect = branch.intValue() + 1

        setConfBranch(IntNode(respect), "respect", author.id)

        return author
    }

    private fun getRespect(author: IUser): Int = getConfBranch("respect", author.id).intValue()

    private fun getTopRespects(): String = /* Oh the laziness */
            getConfBranch("respect").fields()
                    .asSequence()
                    .sortedWith(Comparator.comparingInt { it.value.intValue() })
                    .toList()
                    .reversed()
                    .fold("") { str, ele ->
                        "$str\n${client.getUserByID(ele.key).name}: ${ele.value}"
                    }


}