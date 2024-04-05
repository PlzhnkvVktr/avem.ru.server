package avem.ru.data.images

import avem.ru.data.model.Image
import avem.ru.requests.AddImageRequest
import avem.ru.requests.AddNewsRequest

interface ImagesDataSource {

    suspend fun getAllImages(): List<Image>

    suspend fun findById(id: String): Image?

    suspend fun addImage(image: Image): Boolean

    suspend fun deleteImageById(imageId: String): Boolean

    suspend fun updateImageById(imageId: String, newsRequest: AddImageRequest): Boolean
}