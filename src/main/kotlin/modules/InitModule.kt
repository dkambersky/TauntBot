package modules

import Module
import client
import io.get
import sx.blah.discord.handle.obj.Status
import java.lang.Thread.sleep

/**
 * Module which handles the loading of the bot
 */
class InitModule : Module() {

    override fun handleReady() {
        super.handleReady()
        sleep(150)

        client.changeStatus(Status.game("with JVM"))

        try {
            val channelID = get("master-channel-id") as String?
            client.getChannelByID(channelID).sendMessage("MurderBot online!")
        } catch (e: Exception) {
            println("Please specify master-channel-id for the bot to work properly.")
        }


    }

}