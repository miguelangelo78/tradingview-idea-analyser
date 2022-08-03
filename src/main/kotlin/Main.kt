fun main(args: Array<String>) {
    val results = mutableListOf<IdeasResult>()

    for(i in 1..500) {
        val result = Ideas.getAllIdeasForPair("btcusd", i)
        results.add(result)

        println("Fetched idea for page ${i}:")
        IdeaOverviews.logIdeaOverviews(result.ideaOverviws)
        Ideas.logIdeas(result.ideas)
    }

    // Do what you want with 'results' here
}
