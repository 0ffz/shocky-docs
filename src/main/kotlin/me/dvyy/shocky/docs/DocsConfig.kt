package me.dvyy.shocky.docs

import kotlinx.serialization.Serializable

@Serializable
data class DocsConfig(
    val name: String = "Docs",
)
