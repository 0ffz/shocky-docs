import me.dvyy.shocky.docs.docsTemplate
import me.dvyy.shocky.AssetSource
import me.dvyy.shocky.shocky
import kotlin.io.path.Path
import kotlin.io.path.div

suspend fun main(args: Array<String>) = shocky(
    watch = listOf(Path("docs"))
) {
    dest("out")
    assets("docs/assets")
    assetsFromResources(
        "assets/scripts/prism.js",
        "assets/scripts/prism.css",
        "assets/scripts/theme-duotone-light.css",
        "assets/scripts/theme-darcula.css",
        "assets/fonts/JetBrainsMono-Regular.woff2",
    )
    assets(AssetSource.ResourcesFolder("custom.css", Path("build/extracted")))
    siteRoot("docs")
    tailwind {
        inputCss = Path("build/extracted") / "custom.css"
    }
    routing {
        template("default") { docsTemplate() }
        includeDirectory(".")
    }
}.run(args)
