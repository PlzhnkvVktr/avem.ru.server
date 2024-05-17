package avem.ru.data.products

import avem.ru.data.model.Product
import avem.ru.requests.AddProductRequest

interface ProductsDataSource {
    suspend fun getAllProducts(): List<Product>

    suspend fun getProductsByCategory(category: Int): List<Product>?
    suspend fun getProductsBySubcategory(subcategory: Int): List<Product>?
    suspend fun findByName(name: String): List<Product>?
    suspend fun findById(id: String): Product?

    suspend fun addProduct(product: Product): Boolean

    suspend fun deleteProductForId(productId: String): Boolean

    suspend fun updateProductById(productId: String, productRequest: AddProductRequest): Boolean
}