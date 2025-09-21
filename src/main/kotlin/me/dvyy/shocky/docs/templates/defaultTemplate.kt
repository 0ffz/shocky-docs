package me.dvyy.shocky.docs.templates

import kotlinx.html.*
import me.dvyy.shocky.docs.DocsConfig
import me.dvyy.shocky.docs.docsBody
import me.dvyy.shocky.icons.Icons
import me.dvyy.shocky.markdown
import me.dvyy.shocky.page.Page
import me.dvyy.shocky.site

context(page: Page)
inline fun defaultTemplate(
    crossinline init: FlowContent.() -> Unit = { markdown(Icons.renderFromMarkdown(page.content)) },
) = page.html {
    val docsConfig = site.inject<DocsConfig>()
    head {
        meta(charset = "utf-8")
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        link(rel = LinkRel.stylesheet, href = "${site.rootUrl}/assets/tailwind/styles.css")
        link(rel = "icon", href = "${site.rootUrl}/${docsConfig.favicon}")

        if (docsConfig.usePrism) {
            script(src = "${site.rootUrl}/assets/scripts/prism.js") {}
            link(rel = LinkRel.stylesheet, href = "${site.rootUrl}/assets/scripts/prism.css")
            link(rel = LinkRel.stylesheet, href = "${site.rootUrl}/assets/scripts/theme-darcula.css")
        }

        // Mermaid themes witcher
        if (docsConfig.useMermaid) script(type = "module") {
            unsafe {
                +"""
                import mermaid from 'https://cdn.jsdelivr.net/npm/mermaid@11/dist/mermaid.esm.min.mjs';
                var mermaidTheme = window.matchMedia('(prefers-color-scheme: light)').matches ? "default" : "dark";
                mermaid.initialize({ startOnLoad: true, theme: mermaidTheme, });
                """.trimIndent()
            }
        }
        // Theme switcher script
        if (docsConfig.usePrism) script(type = "text/javascript") {
            unsafe {
                +"""
                (function(){
                    var light = '${site.rootUrl}/assets/scripts/theme-duotone-light.css';
                    var dark = '${site.rootUrl}/assets/scripts/theme-darcula.css';
                    // If the media query isn't supported, the dark theme will be used as default
                    var theme = window.matchMedia('(prefers-color-scheme: light)').matches ? light : dark;

                    var link = document.createElement('link');
                    link.rel = 'stylesheet';
                    link.type = 'text/css';
                    link.href = theme;
                    document.head.appendChild(link);

                    // Listen for changes in the OS settings
                    window.matchMedia('(prefers-color-scheme: light)').addEventListener('change', e => {
                        link.href = e.matches ? light : dark;
                    });
                })();
                """
            }
        }

        title("${docsConfig.name} - ${page.meta.title}")
    }
    body(classes = "bg-zinc-50 dark:bg-zinc-900 min-h-screen overflow-x-hidden") {
        docsBody(docsConfig) {
            init()
        }
    }
}
