package avem.ru.requests

import avem.ru.data.model.Subcategory
import kotlinx.serialization.Serializable

@Serializable
data class AddCategoryRequest(
    val name: String,
    val path: String,
    val subcategories: List<SubcategoryRequest>
)

@Serializable
data class SubcategoryRequest(
    val name: String,
    val path: String,
    val card_img: String,
)

