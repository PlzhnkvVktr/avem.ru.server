package avem.ru

import avem.ru.data.images.ImagesDataSourceImpl
import avem.ru.data.news.NewsDataSourceImpl
import avem.ru.data.products.ProductsDataSourceImpl
import avem.ru.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val dbName = "avem"
    val db = KMongo.createClient(
        connectionString = "mongodb://localhost:27017/$dbName"
    ).coroutine.getDatabase(dbName)
    val newsDataSource = NewsDataSourceImpl(db)
    val productsDataSource = ProductsDataSourceImpl(db)
    val imagesDataSource = ImagesDataSourceImpl(db)
    configureHTTP()
    configureSerialization()
    configureRouting(
        newsDataSource,
        productsDataSource,
        imagesDataSource
    )
}
