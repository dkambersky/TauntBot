package io

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.NoSuchFileException

/** Default IDEA comment
 * Created by David on 06/02/2017.
 */


var db_initialized = false
var db_tree: JsonNode? = null

/* Convenience method */
fun getConfBranch(vararg keys: String): JsonNode {
    load()
    var node: JsonNode = db_tree!!

    for (key in keys) {

        val value = node.findValue(key)

        if (value != null && value.isValueNode)
            return value

        else node = node.with(key)

    }

    return node
}

fun setConfBranch(value: JsonNode, vararg keys: String) {
    load()
    var node = db_tree!!

    for (key in keys.take(keys.size - 1)) {
        node = node.with(key)
    }

    (node as ObjectNode).set(keys.last(), value)
    save()

}

private fun load() {

    if (db_initialized)
        return

    val fac = YAMLFactory()
    val mapper = ObjectMapper(fac)

    try {
        val parser = fac.createParser(Files.newInputStream(File(get("db-path") as String).toPath()))
        db_tree = mapper.readTree(parser)
    } catch (e: NoSuchFileException) {
        println("No database file found. Creating a default one.")
        setDefaults()
        save()

    }


}

private fun save() {
    ObjectMapper(YAMLFactory()).writeValue(File(get("db-path") as String), db_tree)
}

private fun setDefaults() {
    val mapper = ObjectMapper(YAMLFactory())
    val root: ObjectNode = mapper.createObjectNode()

    val harambe_node = mapper.createObjectNode()
    harambe_node.set("streak", TextNode("0"))
    harambe_node.set("last", TextNode("${System.currentTimeMillis()}"))

    root.set("harambe", harambe_node)

    db_tree = root

}