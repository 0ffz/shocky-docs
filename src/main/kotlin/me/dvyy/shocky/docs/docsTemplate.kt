package me.dvyy.shocky.docs

import kotlinx.html.*
import me.dvyy.shocky.icons.Icons
import me.dvyy.shocky.markdown
import me.dvyy.shocky.page.Page
import me.dvyy.shocky.site

context(page: Page)
inline fun docsTemplate(
    crossinline init: FlowContent.() -> Unit = { markdown(Icons.renderFromMarkdown(page.content)) },
) = page.html {
    val docsConfig = site.readFile<DocsConfig>("config.yml")
    head {
        meta(charset = "utf-8")
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        link(rel = LinkRel.stylesheet, href = "/assets/tailwind/styles.css")
        script(src = "/assets/scripts/prism.js") {}
        link(rel = LinkRel.stylesheet, href = "/assets/scripts/prism.css")
        link(rel = LinkRel.stylesheet, href = "/assets/scripts/theme-darcula.css")

        // Theme switcher script
        script(type = "text/javascript") {
            unsafe {
                +"""
                (function(){
                    var light = '/assets/scripts/theme-duotone-light.css';
                    var dark = '/assets/scripts/theme-darcula.css';
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
