import com.google.gson.Gson
import org.jsoup.nodes.Document

class Idea (overview: IdeaOverview, val title: String, val description: String) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}

class IdeasResult(val ideas: List<Idea>, val ideaOverviws: List<IdeaOverview>) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}

object Ideas {
    private const val BASE_URL = "https://www.tradingview.com/chart/"

    fun getAllIdeasForPair(pair: String, page: Int = 1): IdeasResult {
        val ideaOverviews = IdeaOverviews.getIdeaOverviewsForPair(pair, page)
        val ideas = mutableListOf<Idea>();
        ideaOverviews.forEach {
            ideas.add(getIdea(it))
        }
        return IdeasResult(ideas, ideaOverviews)
    }

    fun getIdea(ideaOverview: IdeaOverview): Idea {
        val ideaDoc = getIdeaDoc(ideaOverview)
        val title = ideaDoc.getElementsByClass("tv-chart-view__title-name").first();
        val textContent = ideaDoc.getElementsByClass("tv-chart-view__description").first();
        if(title == null || textContent == null) {
            throw Exception("Could not find title or text content")
        }
        return Idea(ideaOverview, title.text(), textContent.text())
    }

    fun logIdeas(ideas: List<Idea>) {
        ideas.forEach {
            println(it)
        }
    }

    private fun getIdeaDoc(ideaOverview: IdeaOverview): Document {
        val url = "${BASE_URL}${ideaOverview.pair}${ideaOverview.link}"
        return getDocumentForUrl(url)
    }
}