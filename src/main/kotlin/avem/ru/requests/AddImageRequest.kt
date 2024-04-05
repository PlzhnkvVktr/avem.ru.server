package avem.ru.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddImageRequest(
    val src: String
)
