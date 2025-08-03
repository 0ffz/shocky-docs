package me.dvyy.shocky.docs

import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.main

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
