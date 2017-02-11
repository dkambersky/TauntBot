package modules

import Module
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.TextNode
import io.db_tree
import io.getDb
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

        if(msg != "") e.message.channel.sendMessage(msg)


    }

    private fun increaseRespect(author: IUser): IUser {



        (db_tree!!.path("respect") as ObjectNode).set(author.id, IntNode((getDb("respect", author.id) as Int ) +1))

        return author
    }

    fun getRespect(author: IUser): Int{
     return getDb("respect", author.id) as Int
    }

}