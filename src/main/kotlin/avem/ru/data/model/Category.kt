package avem.ru.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Category(
    val name: String,
    val path: String,
    val subcategories: List<Subcategory>,
    @BsonId
    val id: String = ObjectId().toString()
)

