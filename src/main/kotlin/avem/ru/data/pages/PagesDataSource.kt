package avem.ru.data.pages

import avem.ru.data.model.News
import avem.ru.data.model.Page
import avem.ru.requests.AddPageRequest
import org.litote.kmongo.eq

interface PagesDataSource {
    suspend fun getAllPages(): List<Page>
    suspend fun getPageByName(name: String): Page?
    suspend fun addPage(page: Page): Boolean
    suspend fun findById(id: String): Page?
    suspend fun updatePageById(id: String, pageRequest: AddPageRequest): Boolean
    suspend fun deletePageById(pageId: String): Boolean
}