package avem.ru.data.subcategory

import avem.ru.data.model.Subcategory

interface SubcategoryDataSource {
    suspend fun getAllSubcategory(): List<Subcategory>

    suspend fun getSubcategoryByParent(parent: String): Subcategory?

}