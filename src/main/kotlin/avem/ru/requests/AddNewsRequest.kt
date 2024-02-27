package avem.ru.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddNewsRequest(
    val title: String,
    val message: String
)
