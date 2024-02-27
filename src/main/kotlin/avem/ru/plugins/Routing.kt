package avem.ru.plugins

import avem.ru.data.news.NewsDataSource
import avem.ru.data.products.ProductsDataSource
import avem.ru.routes.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    newsData: NewsDataSource,
    productsData: ProductsDataSource
) {
    routing {
        getNewsRoutes(newsData)
        getProductRoutes(productsData)
    }
}
