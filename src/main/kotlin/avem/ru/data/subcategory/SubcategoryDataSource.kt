package avem.ru.data.subcategory

import avem.ru.data.model.Category
import avem.ru.data.model.Subcategory
import avem.ru.requests.AddCategoryRequest
import avem.ru.requests.AddSubcategoryRequest

interface SubcategoryDataSource {
    suspend fun getAllSubcategory(): List<Subcategory>

//    suspend fun getSubcategoryByParent(parent: String): Subcategory?

    suspend fun findById(id: String): Subcategory?
    suspend fun addSubcategory(subcategory: Subcategory): Boolean

    suspend fun deleteSubcategoryById(subcategoryId: String): Boolean
    suspend fun updateSubcategoryById(subcategoryId: String, subcategoryRequest: AddSubcategoryRequest): Boolean


}