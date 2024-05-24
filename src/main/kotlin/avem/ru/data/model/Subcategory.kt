package avem.ru.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Subcategory (
    val name: String,
    val path: String,
    val card_img: String,
    @BsonId
    val id: String = ObjectId().toString()
)