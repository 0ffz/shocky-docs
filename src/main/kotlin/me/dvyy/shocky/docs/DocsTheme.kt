package me.dvyy.shocky.docs

import kotlinx.serialization.Serializable

@Serializable
data class DocsTheme(
    val textColor: String = "orange",
    val highlightColor: String = "orange",
)