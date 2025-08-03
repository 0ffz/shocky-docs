package me.dvyy.shocky.docs

import kotlinx.html.FlowContent
import kotlinx.html.aside
import kotlinx.html.div
import kotlinx.html.id
import me.dvyy.shocky.page.Page

context(page: Page)
fun FlowContent.sidebar() {
    // Sidebar - hidden by default on mobile, fixed position
    aside(
        """
        bg-zinc-50 dark:bg-zinc-900 border-r border-zinc-200 dark:border-zinc-800
        text-zinc-800 dark:text-zinc-100
        pt-14 fixed md:fixed left-0 top-0 h-screen z-10
        max-md:w-full md:w-3xs lg:w-2xs
        overflow-y-auto transition-transform duration-300 -translate-x-full
        md:translate-x-0 shadow-lg md:shadow-none
        min-w-[200px] max-w-[600px]
        """.trimIndent()
    ) {
        id = "docs-sidebar"

        // Navigation content
        div("px-4 py-2") {
//            h2("text-xl font-semibold text-zinc-800 dark:text-zinc-100 mb-4") {
//                +"Contents"
//            }

            // Example navigation items
            navItems()
        }
    }
}
