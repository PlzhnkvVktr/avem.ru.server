package avem.ru.data.news

import avem.ru.data.model.News
import avem.ru.requests.AddNewsRequest

interface NewsDataSource {

    suspend fun getAllNews(): List<News>

    suspend fun findById(id: String): News?

    suspend fun addNews(news: News): Boolean

    suspend fun deleteNewsById(newsId: String): Boolean

    suspend fun updateNewsById(newsId: String, newsRequest: AddNewsRequest): Boolean
}