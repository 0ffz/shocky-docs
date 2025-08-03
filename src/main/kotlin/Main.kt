import docs.docsTemplate
import docs.readResource
import kotlinx.html.A
import kotlinx.html.consumers.delayed
import kotlinx.html.consumers.filter
import kotlinx.html.consumers.onFinalize
import kotlinx.html.consumers.onFinalizeMap
import kotlinx.html.dom.append
import me.dvyy.shocky.AssetSource
import me.dvyy.shocky.page.Page
import me.dvyy.shocky.shocky
import org.w3c.dom.html.HTMLLinkElement
import kotlin.io.path.Path
import kotlin.io.path.createParentDirectories
import kotlin.io.path.div
import kotlin.io.path.writeText

suspend fun main(args: Array<String>) = shocky(
    watch = listOf(Path("site"))
) {
    dest("out")
    assets("site/assets")
    assetsFromResources(
        "assets/scripts/prism.js",
        "assets/scripts/prism.css",
        "assets/scripts/theme-duotone-light.css",
        "assets/scripts/theme-darcula.css",
        "assets/fonts/JetBrainsMono-Regular.woff2",
    )
    assets(AssetSource.ResourcesFolder("custom.css", Path("build/extracted")))
    siteRoot("site")
    tailwind {
        inputCss = Path("build/extracted") / "custom.css"
    }
    routing {
        (dest / "assets/fonts/JetBrainsMono-Regular.woff2")
            .createParentDirectories()
            .writeText(readResource("/assets/fonts/JetBrainsMono-Regular.woff2")!!)
        template("default") {
            docsTemplate()
        }

        includeAssets()
        pages(".")
    }
}.run(args)
