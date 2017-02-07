import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import sx.blah.discord.api.ClientBuilder
import sx.blah.discord.api.IDiscordClient

/**
 * Created by David on 06/02/2017.
 */


const val token = "Stuff!"

class Bot(client: IDiscordClient) {



}

var instance: Bot?= null

fun main(args: Array<String>) {


    println("lol")
    val fac = YAMLFactory()
    
    val builder = ClientBuilder()
    builder.withToken(token)
    try {


    } catch (e: Exception){
        e.printStackTrace()
    }




}


