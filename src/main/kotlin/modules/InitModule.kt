package modules

import Module
import client
import io.get
import sendMsg


/**
 * Module which handles the initial triggers for the bot
 */
class InitModule : Module() {

    override fun handleReady() {
        super.handleReady()
        client.changePlayingText("with JVM")

        try {
            val channelID = get("master-channel-id") as Long
            sendMsg(client.getChannelByID(channelID), "MurderBot online!", 5000)
        } catch (e: Exception) {
            println("Please specify master-channel-id for the bot to work properly.")
        }


    }

}