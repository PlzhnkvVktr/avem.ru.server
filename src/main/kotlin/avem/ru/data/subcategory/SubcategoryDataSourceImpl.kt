package avem.ru.data.subcategory

import avem.ru.data.model.Category
import avem.ru.data.model.Subcategory
import avem.ru.requests.AddSubcategoryRequest
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.replaceOne
import org.litote.kmongo.eq

class SubcategoryDataSourceImpl(
    db: CoroutineDatabase
): SubcategoryDataSource {
    private val subcategories = db.getCollection<Subcategory>()
    override suspend fun getAllSubcategory(): List<Subcategory> =
        subcategories.find().toList()

//    override suspend fun getSubcategoryByParent(parent: String): Subcategory? =
//        subcategories.findOne(Subcategory::parent eq parent)

    override suspend fun findById(id: String): Subcategory? =
        subcategories.findOne(Category::id eq id)

    override suspend fun addSubcategory(subcategory: Subcategory): Boolean =
        subcategories.insertOne(subcategory).wasAcknowledged()

    override suspend fun deleteSubcategoryById(subcategoryId: String): Boolean {
        val currentNews = subcategories.findOne(Category::id eq subcategoryId)
        currentNews?.let { item ->
            return true
        } ?: return false
    }

    override suspend fun updateSubcategoryById(
        subcategoryId: String,
        subcategoryRequest: AddSubcategoryRequest
    ): Boolean =
        findById(subcategoryId)?.let {
                article ->
            val updateResult =
                subcategories.replaceOne(article.copy(
                    name = subcategoryRequest.name,
                    path = subcategoryRequest.path
                ))
            updateResult.modifiedCount == 1L
        } ?: false


}