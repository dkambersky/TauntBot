import io.get
import modules.InitModule
import modules.MooModule
import sx.blah.discord.api.ClientBuilder
import sx.blah.discord.api.IDiscordClient

/**
 * Created by David on 06/02/2017.
 * IDEA keeps complaining about the default comment.
 */

val client = login()

fun main(args: Array<String>) {

    client.dispatcher.registerListener(InitModule())
    client.dispatcher.registerListener(MooModule())

}

@Suppress("UNREACHABLE_CODE")
fun login():IDiscordClient{

    val builder = ClientBuilder()

    val token = get("api-token") as String?
    if(token == null || token == "") {
        throw Exception("Please specify an API token in config.yml!")
    }

    builder.withToken(token)

    try {
        println("Logging in with token: ${builder.token}")
        return builder.login()
    } catch (e: Exception){
        println("Error occurred while logging in!")
        e.printStackTrace()

    }

    /* IDEA complains whether this piece of unreachable
        code is here or not. Oh well */
    return null!!
}