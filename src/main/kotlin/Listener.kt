import sx.blah.discord.api.events.Event
import sx.blah.discord.api.events.IListener
import sx.blah.discord.handle.impl.events.MessageReceivedEvent
import sx.blah.discord.handle.impl.events.ReadyEvent
import sx.blah.discord.handle.obj.Status

/**
 * Main listener class.
 * This would probably look nicer with the annotation approach,
 *  but hey, I like interfaces.
 */
class Listener : IListener<Event> {

    var ready = false

    override fun handle(e: Event?) {

        /* Wait for ReadyEvent */
        if(e is ReadyEvent) ready = true
        if (!ready) return


        /* Handle correct event type */
        when (e) {
            is MessageReceivedEvent -> handleMsgRec(e)

        }
    }



    fun handleMsgRec(e: MessageReceivedEvent){
        client.changeStatus(Status.game(e.message.content))

    }
}