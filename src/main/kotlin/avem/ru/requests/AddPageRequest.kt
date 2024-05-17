package avem.ru.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddPageRequest(
    val name: String,
    val html: String,
    val path: String,
    val isVisibility: Boolean,
    val isNavbar: Boolean
)
