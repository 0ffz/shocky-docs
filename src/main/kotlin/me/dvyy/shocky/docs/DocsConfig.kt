package me.dvyy.shocky.docs

import kotlinx.serialization.Serializable

@Serializable
data class DocsConfig(
    val name: String = "Docs",
    val siteUrl: String = "",
    val favicon: String = "assets/favicon.svg",
    val useMermaid: Boolean = true,
    val usePrism: Boolean = true,
    val theme: DocsTheme = DocsTheme(),
    val redirects: Map<String, String> = emptyMap(),
)
