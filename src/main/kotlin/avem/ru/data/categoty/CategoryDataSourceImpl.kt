package avem.ru.data.categoty

import avem.ru.data.model.Category
import avem.ru.data.model.News
import avem.ru.data.model.Product
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class CategoryDataSourceImpl(
    db: CoroutineDatabase
): CategoryDataSource {
    private val categories = db.getCollection<Category>()

    override suspend fun getAllCategories(): List<Category> =
        categories.find().toList()

    override suspend fun findById(id: String): Category? =
        categories.findOne(Category::id eq id)

    override suspend fun addCategory(category: Category): Boolean =
        categories.insertOne(category).wasAcknowledged()

    override suspend fun deleteCategoryById(categoryId: String): Boolean {
        val currentNews = categories.findOne(Category::id eq categoryId)
        currentNews?.let { item ->
            return categories.deleteOneById(item.id).wasAcknowledged()
        } ?: return false
    }

}