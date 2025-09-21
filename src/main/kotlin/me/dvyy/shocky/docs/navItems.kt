package me.dvyy.shocky.docs

import kotlinx.html.FlowContent
import kotlinx.html.ul
import kotlinx.html.unsafe
import me.dvyy.shocky.icons.Icons
import me.dvyy.shocky.markdownToHTML
import me.dvyy.shocky.page.Page
import me.dvyy.shocky.site

context(page: Page)
fun FlowContent.navItems() {
    // This could be dynamically generated based on your documentation structure
    ul("sidebar-content space-y-1") {
        val content = site.pages["SUMMARY.md"]?.content ?: ""
        val currentUrl = page.meta.url
        val renderedContent = Icons.renderFromMarkdown(content
            .markdownToHTML()
        )
            .replace("""href="$currentUrl"""", """href="$currentUrl" class="selected"""")
        unsafe { +renderedContent }
    }
}
