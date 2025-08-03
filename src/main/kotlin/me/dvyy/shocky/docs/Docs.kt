package me.dvyy.shocky.docs

fun String.renderMarkdownIcons(): String = replace(":[\\w-]+:".toRegex()) {
    val icon = it.value.removeSurrounding(":")
    val iconText = readResource("/icons/$icon.svg")
    iconText?.replace("\n", "")
        ?.replace("<svg", "<svg class=\"icon\"") ?: ":$icon:"
}

fun readResource(path: String): String? {
    return object {}.javaClass.getResource(path)?.readText()
}
