package avem.ru.plugins

import avem.ru.data.categoty.CategoryDataSource
import avem.ru.data.categoty.CategoryDataSourceImpl
import avem.ru.data.images.ImagesDataSource
import avem.ru.data.news.NewsDataSource
import avem.ru.data.pages.PagesDataSource
import avem.ru.data.products.ProductsDataSource
import avem.ru.data.subcategory.SubcategoryDataSource
import avem.ru.routes.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting(
    newsData: NewsDataSource,
    productsData: ProductsDataSource,
    imagesData: ImagesDataSource,
    pageData: PagesDataSource,
    categoryData: CategoryDataSource,
    subcategoryData: SubcategoryDataSource
) {
    routing {
        staticFiles("/static", File("static"))
        getNewsRoutes(newsData)
        getProductRoutes(productsData)
        getImageRoutes(imagesData)
        getPageRoutes(pageData)
        getCategoryRoutes(categoryData)
        getSubcategoryRoutes(subcategoryData)
    }
}
