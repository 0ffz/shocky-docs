package me.dvyy.shocky.docs

import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.div
import me.dvyy.shocky.markdown
import me.dvyy.shocky.page.Page
import me.dvyy.shocky.page.Pages
import me.dvyy.shocky.site
import kotlin.io.path.Path

context(page: Page)
fun FlowContent.topBar(config: DocsConfig) {
    div(
        """
        col-span-full bg-zinc-50 dark:bg-zinc-900 border-b
        border-zinc-200 dark:border-zinc-700 h-14 fixed top-0 left-0 right-0 z-20 
        flex items-center justify-between px-4
        text-nowrap text-clip
        """.trimIndent()
    ) {
        // Logo/Title
        a(href = "/", classes = " text-xl font-bold text-zinc-800 dark:text-zinc-100") {
            +config.name
        }

        val topbarContent = site.pages["TOPBAR.md"]?.content

        div("topbar-content flex items-center gap-4") {
            div("gap-4 max-sm:hidden") {
                markdown(topbarContent?.renderMarkdownIcons() ?: "")
            }

            // Mobile menu toggle button (only visible on small screens)
            button(classes = "md:hidden p-2 rounded-md hover:bg-zinc-200 dark:hover:bg-zinc-700") {
                attributes["aria-label"] = "Toggle navigation menu"
                attributes["onclick"] = "document.getElementById('docs-sidebar').classList.toggle('-translate-x-full')"

                // Hamburger icon
                div("w-6 space-y-1.5") {
                    div("w-full h-0.5 bg-zinc-600 dark:bg-zinc-300")
                    div("w-full h-0.5 bg-zinc-600 dark:bg-zinc-300")
                    div("w-full h-0.5 bg-zinc-600 dark:bg-zinc-300")
                }
            }
        }
    }
}
