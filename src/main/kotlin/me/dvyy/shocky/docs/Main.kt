package me.dvyy.shocky.docs

import me.dvyy.shocky.AssetSource
import me.dvyy.shocky.Site
import me.dvyy.shocky.docs.templates.defaultTemplate
import me.dvyy.shocky.docs.templates.landingTemplate
import me.dvyy.shocky.shocky
import kotlin.io.path.Path
import kotlin.io.path.div

suspend fun main(args: Array<String>) {
    val path = args.indexOf("--path").takeIf { it >= 0 }?.let { args[it + 1] } ?: "docs"
    shocky(
        watch = listOf(Path(path))
    ) {
        val docsConfig = Site.readFile<DocsConfig>(Path(path) / "config.yml", default = { DocsConfig() })
        provide<DocsConfig>(docsConfig)
        provide<DocsTheme>(docsConfig.theme)

        siteUrl = docsConfig.siteUrl

        dest("out")
        assets("$path/assets")
        assetsFromResources(
            "assets/favicon.svg",
            "assets/scripts/prism.js",
            "assets/scripts/prism.css",
            "assets/scripts/theme-duotone-light.css",
            "assets/scripts/theme-darcula.css",
            "assets/fonts/JetBrainsMono-Regular.woff2",
        )
        assets(AssetSource.ResourcesFolder("custom.css", Path("build/extracted")))
        siteRoot(path)
        tailwind {
            inputCss = Path("build/extracted") / "custom.css"
        }
        routing {
            template("default") { defaultTemplate() }
            template("landing") { landingTemplate() }
            includeDirectory(".")
        }
    }.run(args)
}
