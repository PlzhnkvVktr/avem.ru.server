package avem.ru.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Product (
    val name: String,
    val description: String,
    val characteristic: String,
    val specification: String,
    val additionally: String,
    val category: Int,
    val images: List<String>,
    @BsonId
    val id: String = ObjectId().toString()
)
