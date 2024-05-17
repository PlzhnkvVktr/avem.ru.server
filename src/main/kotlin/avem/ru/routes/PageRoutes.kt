package avem.ru.routes

import avem.ru.data.model.Page
import avem.ru.data.pages.PagesDataSource
import avem.ru.requests.AddNewsRequest
import avem.ru.requests.AddPageRequest
import avem.ru.response.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.getPageRoutes(
    pageData: PagesDataSource
) {
    get("/pages") {
        val allPages = pageData.getAllPages()
        call.respond(allPages)
    }

    get("/pages/{id}") {
        val id = call.parameters["id"].toString()
        val articleById = pageData.findById(id)
        articleById?.let {
            call.respond(articleById)
        } ?: call.respond(HttpStatusCode.BadRequest)
    }

    put("/pages/{id}") {
        val id = call.parameters["id"].toString()

        val page = call.receive<AddPageRequest>()
        val updatedSuccessfully = pageData.updatePageById(id, page)
        if (updatedSuccessfully) {
            call.respond(HttpStatusCode.OK, "Article was edited")
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    post("/pages") {
        val request = try {
            call.receive<AddPageRequest>()
        } catch (e: CannotTransformContentToTypeException) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val areFieldsBlank = request.html.isBlank() || request.name.isBlank()
        if (areFieldsBlank) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val page = Page(
            name = request.name,
            html = request.html,
            path = request.path,
            isVisibility = request.isVisibility,
            isNavbar = request.isNavbar
        )
        val wasAcknowledged = pageData.addPage(page)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        call.respond(HttpStatusCode.OK)
    }

    delete("pages/{id}"){

        val id = call.parameters["id"].toString()

        if (pageData.deletePageById(id)) {
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
}