package me.dvyy.shocky.docs

import kotlinx.html.FlowContent
import kotlinx.html.div
import me.dvyy.shocky.page.Page

// Main documentation layout using grid
context(page: Page)
fun FlowContent.docsBody(config: DocsConfig, content: FlowContent.() -> Unit) {
    div("grid grid-cols-1 md:grid-cols-[var(--container-3xs)_1fr] lg:grid-cols-[var(--container-2xs)_1fr] min-h-screen") {
        // Top navigation bar (visible on all screen sizes)
        topBar(config)

        // Sidebar (visible on md+ screens, hidden behind toggle on smaller screens)
        sidebar()

        // Main content area
        mainContent(content)

        footer()
    }
}
