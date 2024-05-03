package avem.ru.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddProductRequest(
    val name: String,
    val card_img: String,
    val description: String,
    val characteristic: String,
    val specification: String,
    val additionally: String,
    val category: Int,
    val subcategory: Int,
    val images: List<String>
)
