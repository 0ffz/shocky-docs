package me.dvyy.shocky.docs.templates

import kotlinx.html.*
import me.dvyy.shocky.icons.Icons
import me.dvyy.shocky.markdown
import me.dvyy.shocky.page.Page


context(page: Page)
inline fun landingTemplate(
    crossinline init: FlowContent.() -> Unit = { markdown(Icons.renderFromMarkdown(page.content)) },
) = defaultTemplate {
    val landingMeta = page.meta<LandingMeta>()
    h1 { +page.meta.title }
    init()
    div("grid grid-cols-1 md:grid-cols-2 gap-4") {
        landingMeta.items.forEach {
            card(it)
        }
    }
}

fun FlowContent.card(item: LandingMeta.Item) =
    div("items-center p-4 bg-zinc-50 dark:bg-zinc-800 rounded-lg border border-zinc-200 dark:border-zinc-700") {
        a(href = item.url, classes = "flex items-center w-full gap-4") {
            item.icon?.let { icon ->
                div("flex-none text-${item.color}-500") {
                    unsafe { +Icons.renderFromMarkdown(":$icon:") }
                }
            }
            div("flex-grow") {
                div("text-lg font-medium text-zinc-900 dark:text-zinc-100") {
                    +item.title
                }
                div("text-zinc-600 dark:text-zinc-400") {
                    +item.desc
                }
            }
        }
    }