import org.jsoup.Jsoup
import org.jsoup.nodes.Document

fun getDocumentForUrl(url: String): Document {
    return Jsoup
        .connect(url)
        .userAgent("Mozilla")
        .timeout(5000)
        .referrer("https://www.google.com")
        .get()
}