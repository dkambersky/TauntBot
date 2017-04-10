import io.get
import modules.InitModule
import modules.MooModule
import modules.TournamentModule
import sx.blah.discord.api.ClientBuilder
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IMessage
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


/**
 * Created by David on 06/02/2017.
 * Main class. Autogenerated comment.
 *
 */

/**
 * Idea list:
 *
 *
 * -- News etc
 * - TODO github notifications
 * - TODO rss feed
 * - TODO HN & reddit digests
 * - TODO monitoring - servers etc
 *
 * -- Interactivity
 * + DONE fortune, respects etc (because we can)
 * - TODO taunts & replies
 * - TODO economy, slots, rolls
 *
 * -- Functionality
 * - TODO delete responses after set delay
 * - TODO purge messages by channels / users / time!
 *
 * -- Logistics
 * - TODO save module settings into separate YAML nodes
 * - TODO plain ol' user login (trivial)
 * - TODO gradle > maven!
 *
 * -- Large undertakings
 * - TODO musicbot
 * - TODO Fate RP integration (w/ dusanp/ServerFateSR)
 *
 *
 */

val client = login()

/* For timed tasks */
val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()


fun main(args: Array<String>) {
    val a = AsciiDrawer()

    a.drawTextLine("asdf", 3, 7)

    a.drawTextLine("xx", 0, 0)

    a.drawBox(10, 10,5,5)
    a.drawBox(0,3,3,5)
    println(a.toString())


    /*client.dispatcher.registerListener(InitModule())
    client.dispatcher.registerListener(MooModule())
    client.dispatcher.registerListener(TournamentModule())*/

}

@Suppress("UNREACHABLE_CODE")
fun login(): IDiscordClient {

    val builder = ClientBuilder()

    val token = get("api-token") as String?
    if (token == null || token == "") {
        throw Exception("Please specify an API token in config.yml!")
    }

    builder.withToken(token)

    try {
        println("Logging in with token: ${builder.token}")
        return builder.login()
    } catch (e: Exception) {
        println("Error occurred while logging in!")
        e.printStackTrace()

    }

    /* IDEA complains whether this piece of unreachable
        code is here or not. Oh well */
    return null!!
}

fun sendMsg(channel: IChannel, message: String): IMessage {
    return channel.sendMessage((message))
}

fun sendMsg(channel: IChannel, message: String, timeout: Long): IMessage {
    val msg = sendMsg(channel, message)
    executor.schedule({ msg.delete() }, timeout, TimeUnit.MILLISECONDS)

    return msg

}