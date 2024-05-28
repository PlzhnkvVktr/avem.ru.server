package avem.ru.data.model

import avem.ru.requests.SubcategoryRequest
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Category(
    val name: String,
    val path: String,
    val subcategories: List<SubcategoryRequest>,
    @BsonId
    val id: String = ObjectId().toString()
)

