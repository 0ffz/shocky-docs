package me.dvyy.shocky.docs

import me.dvyy.shocky.AssetSource
import me.dvyy.shocky.shocky
import kotlin.io.path.Path
import kotlin.io.path.div

suspend fun main(args: Array<String>) {
    val path = args.indexOf("--path").takeIf { it >= 0 }?.let { args[it + 1] } ?: "docs"
    shocky(
        watch = listOf(Path(path))
    ) {
        dest("out")
        assets("docs/assets")
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
            template("default") { docsTemplate() }
            includeDirectory(".")
        }
    }.run(args)
}
