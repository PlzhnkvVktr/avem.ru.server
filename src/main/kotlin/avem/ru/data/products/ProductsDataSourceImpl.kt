package avem.ru.data.products

import avem.ru.data.model.Product
import avem.ru.requests.AddProductRequest
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.replaceOne
import org.litote.kmongo.eq

class ProductsDataSourceImpl(
    db: CoroutineDatabase
): ProductsDataSource {

    private val products = db.getCollection<Product>()

    override suspend fun getAllProducts(): List<Product> =
        products.find().descendingSort(Product::name).toList()

    override suspend fun findById(id: String): Product? =
        products.findOne(Product::id eq id)

   override suspend fun getProductsByCategory(category: Int): List<Product> =
        products.find(Product::category eq category).toList()

    override suspend fun addProduct(product: Product) =
        this.products.insertOne(product).wasAcknowledged()

    override suspend fun deleteProductForId(productId: String): Boolean {
        val currentNews = products.findOne(Product::id eq productId)
        currentNews?.let { item ->
            return products.deleteOneById(item.id).wasAcknowledged()
        } ?: return false
    }

    override suspend fun updateProductById(productId: String, productRequest: AddProductRequest): Boolean =
        findById(productId)?.let {
            article ->
                val updateResult =
                    products.replaceOne(article.copy(
                        name = productRequest.name,
                        card_img = productRequest.card_img,
                        description = productRequest.description,
                        characteristic = productRequest.characteristic,
                        specification = productRequest.specification,
                        additionally = productRequest.additionally,
                        category = productRequest.category,
                        images = productRequest.images
                    ))
                updateResult.modifiedCount == 1L
            } ?: false

}

