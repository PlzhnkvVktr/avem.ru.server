package avem.ru.plugins

import avem.ru.data.images.ImagesDataSource
import avem.ru.data.news.NewsDataSource
import avem.ru.data.products.ProductsDataSource
import avem.ru.routes.getImageRoutes
import avem.ru.routes.getNewsRoutes
import avem.ru.routes.getProductRoutes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting(
    newsData: NewsDataSource,
    productsData: ProductsDataSource,
    imagesData: ImagesDataSource
) {
    routing {
        staticFiles("/static", File("static"))
        getNewsRoutes(newsData)
        getProductRoutes(productsData)
        getImageRoutes(imagesData)
    }
}
