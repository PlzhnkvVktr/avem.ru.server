package avem.ru.data.news

import avem.ru.data.model.News
import avem.ru.requests.AddNewsRequest
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.replaceOne
import org.litote.kmongo.eq

class NewsDataSourceImpl(
    db: CoroutineDatabase
): NewsDataSource {

    private val news = db.getCollection<News>()

    override suspend fun getAllNews(): List<News> =
        news.find().descendingSort(News::timestamp).toList()

    override suspend fun findById(id: String): News? =
        news.findOne(News::id eq id)

    override suspend fun addNews(news: News): Boolean =
        this.news.insertOne(news).wasAcknowledged()

    override suspend fun deleteNewsById(newsId: String): Boolean {
        val currentNews = news.findOne(News::id eq newsId)
        currentNews?.let { item ->
            return news.deleteOneById(item.id).wasAcknowledged()
        } ?: return false
    }

    override suspend fun updateNewsById(newsId: String, newsRequest: AddNewsRequest): Boolean =
        findById(newsId)?.let {
                article ->
            val updateResult =
                news.replaceOne(article.copy(
                    title = newsRequest.title,
                    message = newsRequest.message
                ))
            updateResult.modifiedCount == 1L
        } ?: false

}