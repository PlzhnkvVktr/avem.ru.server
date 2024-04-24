package avem.ru.data.pages

import avem.ru.data.model.Page
import avem.ru.requests.AddPageRequest
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.replaceOne
import org.litote.kmongo.eq

class PagesDataSourceImpl(
    db: CoroutineDatabase
): PagesDataSource {

    private val pages = db.getCollection<Page>()
    override suspend fun getAllPages(): List<Page> =
        pages.find().toList()

    override suspend fun findById(id: String): Page? =
        pages.findOne(Page::id eq id)

    override suspend fun getPageByName(name: String): Page? =
        pages.findOne(Page::name eq name)

    override suspend fun addPage(page: Page): Boolean =
        pages.insertOne(page).wasAcknowledged()

    override suspend fun updatePageById(id: String, pageRequest: AddPageRequest): Boolean =
        findById(id)?.let {
                article ->
            val updateResult =
                pages.replaceOne(article.copy(
                    html = pageRequest.html,
                    visibility = pageRequest.visibility
                ))
            updateResult.modifiedCount == 1L
        } ?: false
}