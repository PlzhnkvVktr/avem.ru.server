package avem.ru.routes

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

fun Route.getNewsRoutes(
    newsData: NewsDataSource
) {
    get("/news") {
        val allNews = newsData.getAllNews()
        call.respond(allNews)
    }

    get("/news/{id}") {
        val id = call.parameters["id"].toString()
        val articleById = newsData.findById(id)
        articleById?.let {
            call.respond(articleById)
        } ?: call.respond(HttpStatusCode.BadRequest)
    }

    post("/news") {
        val request = try {
            call.receive<AddNewsRequest>()
        } catch (e: CannotTransformContentToTypeException) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val areFieldsBlank = request.title.isBlank() || request.message.isBlank()
        if (areFieldsBlank) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val news = News(
            title = request.title,
            message = request.message,
            timestamp = Date().time
        )
        val wasAcknowledged = newsData.addNews(news)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        call.respond(HttpStatusCode.OK)
    }

    delete("/news/{id}") {

        val id = call.parameters["id"].toString()

        if (newsData.deleteNewsForId(id)) {
            call.respond(
                HttpStatusCode.OK,
                Response(true, "Employee successfully deleted")
            )
        } else {
            call.respond(
                HttpStatusCode.OK,
                Response(true, "Employee not found")
            )
        }
    }

    put("/news/{id}") {
        val id = call.parameters["id"].toString()

        val news = call.receive<AddNewsRequest>()
        val updatedSuccessfully = newsData.updateNewsById(id, news)
        if (updatedSuccessfully) {
            call.respond(HttpStatusCode.OK, "Article was edited")
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

}
