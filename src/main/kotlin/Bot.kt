import com.sun.javaws.exceptions.InvalidArgumentException
import sx.blah.discord.api.ClientBuilder
import sx.blah.discord.api.IDiscordClient

/**
 * Created by David on 06/02/2017.
 */

var client = login()





fun main(args: Array<String>) {

    println("Main running!")





}


fun login():IDiscordClient{

    val builder = ClientBuilder()

    val token = get("api-token") as String?
    if(token == null || token.equals("")) {
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

    return null!!
}