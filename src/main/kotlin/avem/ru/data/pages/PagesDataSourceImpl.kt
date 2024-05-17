package avem.ru.data.pages

import avem.ru.data.model.News
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

    override suspend fun deletePageById(pageId: String): Boolean {
        val currentNews = pages.findOne(Page::id eq pageId)
        currentNews?.let { item ->
            return pages.deleteOneById(item.id).wasAcknowledged()
        } ?: return false
    }

    override suspend fun updatePageById(id: String, pageRequest: AddPageRequest): Boolean =
        findById(id)?.let {
                article ->
            val updateResult =
                pages.replaceOne(article.copy(
                    name = pageRequest.name,
                    html = pageRequest.html,
                    path = pageRequest.path,
                    isVisibility = pageRequest.isVisibility,
                    isNavbar = pageRequest.isNavbar
                ))
            updateResult.modifiedCount == 1L
        } ?: false
}