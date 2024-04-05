package avem.ru.data.images

import avem.ru.data.model.Image
import avem.ru.data.model.News
import avem.ru.requests.AddImageRequest
import avem.ru.requests.AddNewsRequest
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.replaceOne
import org.litote.kmongo.eq

class ImagesDataSourceImpl(
    db: CoroutineDatabase
): ImagesDataSource {

    private val images = db.getCollection<Image>()

    override suspend fun getAllImages(): List<Image> =
        images.find().toList()

    override suspend fun findById(id: String): Image? =
        images.findOne(Image::id eq id)

    override suspend fun addImage(image: Image): Boolean =
        this.images.insertOne(image).wasAcknowledged()

    override suspend fun deleteImageById(imageId: String): Boolean {
        val currentImage = images.findOne(Image::id eq imageId)
        currentImage?.let { item ->
            return images.deleteOneById(item.id).wasAcknowledged()
        } ?: return false
    }

    override suspend fun updateImageById(imageId: String, imageRequest: AddImageRequest): Boolean =
        findById(imageId)?.let {
                article ->
            val updateResult =
                images.replaceOne(article.copy(
                    src = imageRequest.src
                ))
            updateResult.modifiedCount == 1L
        } ?: false

}