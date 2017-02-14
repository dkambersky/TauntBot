package modules

import Module
import com.fasterxml.jackson.databind.node.IntNode
import io.getConfBranch
import io.setConfBranch
import sx.blah.discord.handle.impl.events.MessageReceivedEvent
import sx.blah.discord.handle.obj.IUser

/**
 * Quick little module mimicking moobot, to io.get a hang of the API
 */
class MooModule : Module() {

    override fun handleMessageReceived(e: MessageReceivedEvent) {
        val author = e.message.author
        val msg =
                when (e.message.content.toLowerCase()) {
                    "f" -> "${increaseRespect(author)} pays their respects."
                    "respect" -> "$author has ${getRespect(author)} respect."
                    else -> ""
                }

        if (msg != "") e.message.channel.sendMessage(msg)


    }

    private fun
            increaseRespect(author: IUser): IUser {

        val respectNode = IntNode((getConfBranch("respect", author.id) as IntNode).intValue() + 1)

        setConfBranch(respectNode, "respect", author.id)

        return author
    }

    fun getRespect(author: IUser): Int {
        return getConfBranch("respect", author.id) as Int
    }

}