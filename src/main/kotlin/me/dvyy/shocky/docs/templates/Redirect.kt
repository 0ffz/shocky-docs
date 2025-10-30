package me.dvyy.shocky.docs.templates

import kotlinx.html.*
import me.dvyy.shocky.page.Page

fun Page.redirect(redirectUrl: String) = html {
    head {
        link(rel = LinkRel.stylesheet, href = "${site.rootUrl}/assets/tailwind/styles.css")
        meta {
            httpEquiv = "refresh"
            content = "0; url=$redirectUrl"
        }
    }
    body("bg-zinc-50 dark:bg-zinc-900 min-h-screen flex items-center justify-center") {
        a(href = redirectUrl, classes = "underline") {
            +"Click here if you are not automatically redirected..."
        }
    }
}
