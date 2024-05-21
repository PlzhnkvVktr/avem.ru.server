package avem.ru.routes

import avem.ru.data.categoty.CategoryDataSource
import avem.ru.data.model.News
import avem.ru.data.news.NewsDataSource
import avem.ru.requests.AddNewsRequest
import avem.ru.response.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.getCategoryRoutes(
    categoryData: CategoryDataSource
) {
    get("/category") {
        val allCategory = categoryData.getAllCategories()
        call.respond(allCategory)
    }

    get("/category/{id}") {
        val id = call.parameters["id"].toString()
        val articleById = categoryData.findById(id)
        articleById?.let {
            call.respond(articleById)
        } ?: call.respond(HttpStatusCode.BadRequest)
    }

    post("/category") {

    }

    delete("/category/{id}") {


    }

    put("/category/{id}") {

    }

}