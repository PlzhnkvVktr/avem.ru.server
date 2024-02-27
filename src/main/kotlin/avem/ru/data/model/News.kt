package avem.ru.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class News (
    val title: String,
    val message: String,
    val timestamp: Long,
    @BsonId
    val id: String = ObjectId().toString()

)
