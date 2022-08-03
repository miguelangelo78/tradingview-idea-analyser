import com.google.gson.Gson
import org.jsoup.nodes.Document
import java.util.*

class IdeaOverview(val title: String, val overview: String, val link: String, val pair: String, val page: Int) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}

object IdeaOverviews {
    private const val BASE_URL = "https://www.tradingview.com/ideas/"

    fun getIdeaOverviewsForPair(pair: String, page: Int = 1): MutableList<IdeaOverview> {
        val overviewDocs = getDocOverviewForPair(pair, page)

        val ideaOverviews = mutableListOf<IdeaOverview>()
        val ideaOverviewDocs = overviewDocs.getElementsByClass("tv-feed__item");
        for (ideaOverviewDoc in ideaOverviewDocs) {
            val titleElement = ideaOverviewDoc.getElementsByClass("tv-widget-idea__title-row").first()
            if(titleElement != null) {
                val title = titleElement.text()
                val link = titleElement.getElementsByTag("a").first()?.attr("href")
                val overview = ideaOverviewDoc.getElementsByClass("tv-widget-idea__description-row").first()?.text()

                ideaOverviews.add(IdeaOverview(
                    title,
                    overview ?: "",
                    link ?: "",
                    pair,
                    page
                ))
            }
        }

        return ideaOverviews;
    }

    fun logIdeaOverviews(ideaOverviews: List<IdeaOverview>) {
        println("Idea count: ${ideaOverviews.size}")

        ideaOverviews.forEach {
            println("Idea: ${it.title} - Link: ${it.link}")
            println("Overview:")
            println(it.overview)
            println()
        }
    }

    private fun getDocOverviewForPair(pair: String, page: Int = 1): Document {
        if(page !in 1..500) {
            throw IllegalArgumentException("Page must be between 1 and 500")
        }

        val url = "${BASE_URL}${pair.lowercase(Locale.getDefault())}/page-${page}/"
        return getDocumentForUrl(url)
    }
}