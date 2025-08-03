package docs

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import kotlinx.html.*
import kotlinx.serialization.Serializable
import me.dvyy.shocky.markdown
import me.dvyy.shocky.markdownToHTML
import me.dvyy.shocky.page.Page
import me.dvyy.shocky.page.Pages
import kotlin.io.path.Path
import kotlin.io.path.inputStream

@Serializable
data class DocsConfig(
    val name: String = "Docs",
)

inline fun Page.docsTemplate(
    crossinline init: FlowContent.() -> Unit = { markdown(content.renderMarkdownIcons()) },
) = html {
    val docsConfig = Yaml.default.decodeFromStream<DocsConfig>(Path("site/config.yml").inputStream())
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

        title("${docsConfig.name} - ${page.title}")
    }
    body(classes = "bg-zinc-50 dark:bg-zinc-900 min-h-screen overflow-x-hidden") {
        docsBody(docsConfig) {
            init()
        }
    }
}

// Main documentation layout using grid
context(page: Page)
fun FlowContent.docsBody(config: DocsConfig,content: FlowContent.() -> Unit) {
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

        val topbarContent = Pages.getOrNull(Path("site/TOPBAR.md"), Path("site"))
            ?.read()//.meta<PageStructure>()
            ?.content

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

fun FlowContent.footer() {
    footer("h-32") { }
}

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

@Serializable
class PageStructure(
    val navbar: List<String>,
)

context(page: Page)
fun FlowContent.navItems() {
    // This could be dynamically generated based on your documentation structure
    ul("sidebar-content space-y-1") {
//        navItem("Getting Started", "/docs/getting-started", true)
//        navItem("Installation", "/docs/installation")
//        navItem("Configuration", "/docs/configuration")
        val content = Pages.getOrNull(Path("site/SUMMARY.md"), Path("site"))
            ?.read()//.meta<PageStructure>()
            ?.content ?: ""
        val currentUrl = page.url
        val renderedContent = content
            .renderMarkdownIcons()
            .markdownToHTML()
            .replace("""href="$currentUrl"""", """href="$currentUrl" class="selected"""")
        unsafe { +renderedContent }
        fun subpage(items: UL.() -> Unit) = li("mb-2") {
//            div("font-medium text-zinc-800 dark:text-zinc-200 mb-1") {
//                +"Advanced Topics"
//            }
            ul("pl-2 space-y-1 border-l border-zinc-200 dark:border-zinc-700") {
                items()
            }
        }
//        content.lines().forEach {
//            when {
//                it.startsWith("#") -> {
//                    h2("text-md font-semibold text-zinc-800 dark:text-zinc-100 my-2") {
//                        +it.replace("#+".toRegex(), "").trim()
//                    }
//                }
//                it.trim().startsWith("-") -> {
//                }
//            }
//        }
//        val indentLevel = it.takeWhile { it == ' ' }.length / 2
////                    ul("pl-${indentLevel * 2}") {
//        val navItem = it.trim().removePrefix("-").trim()
//        val childPage = Pages.single(Path("site/$navItem"), Path("site"))
//        val title = childPage.inputFile.useLines { it.firstOrNull() }?.takeIf { it.contains("#") }
//            ?.replace("#+".toRegex(), "")?.trim()
//        navItem(
//            title ?: childPage.readFrontMatter().title, childPage.readFrontMatter().url,
//            active = childPage.url == page.url
//        )
//                    }
//        Pages.walk(Path("site"), Path("site")).map {

//        navItem("Module", "/docs/customization")
//        subpage {
//            navItem("Thing 1", "/docs/customization")
//            navItem("Hello world", "/docs/extensions")
//            navItem("Another", "/docs/plugins")
//        }
    }
}

fun String.renderMarkdownIcons(): String = replace(":[\\w-]+:".toRegex()) {
    val icon = it.value.removeSurrounding(":")
//    when (icon) {
//        "info" -> """<svg  xmlns="http://www.w3.org/2000/svg"  width="20"  height="20"  viewBox="0 0 24 24"  fill="none"  stroke="currentColor"  stroke-width="2"  stroke-linecap="round"  stroke-linejoin="round"  class="icon icon-tabler icons-tabler-outline icon-tabler-info-circle"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M3 12a9 9 0 1 0 18 0a9 9 0 0 0 -18 0" /><path d="M12 9h.01" /><path d="M11 12h1v4h1" /></svg>"""
//        "simple-gradle" -> """<svg  xmlns="http://www.w3.org/2000/svg"  width="20"  height="20"  viewBox="0 0 24 24"  fill="none"  stroke="currentColor"  stroke-width="2"  stroke-linecap="round"  stroke-linejoin="round"  class="icon icon-tabler icons-tabler-outline icon-tabler-brand-github"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M9 19c-4.3 1.4 -4.3 -2.5 -6 -3m12 5v-3.5c0 -1 .1 -1.4 -.5 -2c2.8 -.3 5.5 -1.4 5.5 -6a4.6 4.6 0 0 0 -1.3 -3.2a4.2 4.2 0 0 0 -.1 -3.2s-1.1 -.3 -3.5 1.3a12.3 12.3 0 0 0 -6.2 0c-2.4 -1.6 -3.5 -1.3 -3.5 -1.3a4.2 4.2 0 0 0 -.1 3.2a4.6 4.6 0 0 0 -1.3 3.2c0 4.6 2.7 5.7 5.5 6c-.6 .6 -.6 1.2 -.5 2v3.5" /></svg>"""
//        else -> ":$icon:"
//    }
    val iconText = readResource("/icons/$icon.svg")
    iconText?.replace("\n", "")
        ?.replace("<svg", "<svg class=\"icon\"") ?: ":$icon:"
}

fun readResource(path: String): String? {
    return object {}.javaClass.getResource(path)?.readText()
}

object Theme {
    val textColor = "text-orange"
    val highlightColor = "bg-orange"
}

fun UL.navItem(title: String, href: String, active: Boolean = false) {
    li {
        a(
            href = href,
            classes = "block py-2 md:py-1 px-3 md:px-2  text-sm rounded-md ${if (active) "${Theme.highlightColor}-100 font-semibold dark:${Theme.highlightColor}-900 ${Theme.textColor}-700 dark:${Theme.textColor}-100" else "hover:bg-zinc-200 dark:hover:bg-zinc-800 text-zinc-700 dark:text-zinc-300"}"
        ) {
            +title
        }
    }
}

fun FlowContent.mainContent(content: FlowContent.() -> Unit) {
    main("pt-14 col-start-1 md:col-start-2") {
        div(
            """
            prose prose-zinc dark:prose-invert max-w-none
            prose-a:${Theme.textColor}-500 prose-a:hover:${Theme.textColor}-400
            dark:prose-a:${Theme.textColor}-300 dark:prose-a:hover:${Theme.textColor}-200
            prose-a:no-underline
            prose-img:mx-auto prose-img:my-3
            h-auto
            prose-figcaption:mx-auto prose-figcaption:italic prose-figcaption:mt-0 prose-figcaption:mb-2 prose-figcaption:px-4
            wrapper
            max-md:prose-img:full-bleed
            prose-p:mt-3 prose-p:mb-1 prose-ul:my-3
            prose-li:my-1
            prose-h1:mb-2 prose-h2:mb-1 prose-h3:mb-1 prose-h4:mb-1
            prose-h1:mt-10 prose-h2:mt-8 prose-h3:mt-6 prose-h4:mt-6
            !prose-pre:overflow-hidden
            !prose-code:overflow-hidden
            prose-code:!font-mono prose-code:!break-word
            prose-code:before:content-none prose-code:after:content-none
            prose-pre:rounded-none prose-pre:full-bleed prose-pre:wrapper prose-pre:!px-0
        """.trimIndent()
        ) {
            content()
        }
    }
}
