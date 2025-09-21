package me.dvyy.shocky.docs.templates

import kotlinx.serialization.Serializable

@Serializable
data class LandingMeta(
    val items: List<Item>
) {
    @Serializable
    data class Item(
        val title: String = "",
        val desc: String = "",
        val url: String = "",
        val icon: String? = null,
        val color: String = "orange",
    )
}