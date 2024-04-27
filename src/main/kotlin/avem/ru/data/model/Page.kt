package avem.ru.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Page (
    @BsonId
    val id: String = ObjectId().toString(),
    val name: String,
    val html: String,
    val path: String,
    val visibility: Boolean
)
