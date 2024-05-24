package avem.ru.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddSubcategoryRequest(
    val name: String,
    val path: String,
    val image: String
)
