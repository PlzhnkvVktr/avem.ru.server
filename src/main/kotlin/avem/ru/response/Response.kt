package avem.ru.response

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val status: Boolean,
    val message: String
)
