package avem.ru.data.categoty

import avem.ru.data.model.Category
import avem.ru.data.model.News
import avem.ru.data.model.Product

interface CategoryDataSource {
    suspend fun getAllCategories(): List<Category>
    suspend fun findById(id: String): Category?
    suspend fun addCategory(category: Category): Boolean

    suspend fun deleteCategoryById(categoryId: String): Boolean
}