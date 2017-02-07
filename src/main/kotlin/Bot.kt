import sx.blah.discord.api.ClientBuilder
import sx.blah.discord.api.IDiscordClient

/**
 * Created by David on 06/02/2017.
 */


val token = get("token")
val client = login(token as String)





fun main(args: Array<String>) {

    println("Main running!")





}


fun login(token:String):IDiscordClient{
    val builder = ClientBuilder()
    builder.withToken(token)

    try {
        return builder.login()
    } catch (e: Exception){
        println("Error occurred while logging in!")
        e.printStackTrace()
    }

    return null!!
}