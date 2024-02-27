package avem.ru.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddProductRequest(
    val name: String,
    val description: String,
    val characteristic: String,
    val specification: String,
    val additionally: String,
    val category: Int,
    val images: List<String>
)
