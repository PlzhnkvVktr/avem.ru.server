package avem.ru.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Subcategory (
    val name: String,
    val path: String,
    val parent: String,
    @BsonId
    val id: String = ObjectId().toString()
)