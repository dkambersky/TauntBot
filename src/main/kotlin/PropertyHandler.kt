import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File
import java.nio.file.Files

/**
 * Created by David on 06/02/2017.
 */
const val config_path = "./settings.yml"


var initialized = false
var config = mutableMapOf<String, Any>()


fun get(key: String): Any? {
    load()
    return config[key]
}


private fun load() {

    if (initialized)
        return


    println("Loading properties!")

    val fac = YAMLFactory()
    var mapper = ObjectMapper(fac)


    try {

        println("hello")
        val parser = fac.createParser(Files.newInputStream(File(config_path).toPath()))
        println("hello2")
        val root:JsonNode= mapper.readTree(parser)

        for(node in root){


            println("Parsing node $node.")
        }
    } catch (e: NoSuchFileException) {
        println("Initializing config file!")
        setDefaults()
        save()
    }


}


private fun save() {
    val gen = YAMLFactory().createGenerator(Files.newOutputStream(File(config_path).toPath()))


}


private fun setDefaults() {
    config.put("source-file", "source.txt")
    config.put("api-token", "")

}



