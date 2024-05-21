package avem.ru.requests

data class AddSubcategoryRequest(
    val name: String,
    val path: String,
    val parent: String
)
