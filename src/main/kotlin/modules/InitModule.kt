package modules

import Module
import client
import io.get
import sx.blah.discord.handle.obj.Status
import java.lang.Thread.sleep

/**
 * Created by david on 09.02.2017.
 */
class InitModule : Module() {

    override fun handleReady() {
        super.handleReady()
        sleep(150)
        client.changeStatus(Status.game("with JVM"))
        client.getChannelByID(get("master-channel-id") as String).sendMessage("MurderBot online!")
    }

}