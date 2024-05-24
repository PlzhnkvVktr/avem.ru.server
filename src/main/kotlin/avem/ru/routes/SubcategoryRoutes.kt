package avem.ru.routes

import avem.ru.data.categoty.CategoryDataSource
import avem.ru.data.model.Category
import avem.ru.data.model.Subcategory
import avem.ru.data.subcategory.SubcategoryDataSource
import avem.ru.requests.AddCategoryRequest
import avem.ru.requests.AddSubcategoryRequest
import avem.ru.response.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getSubcategoryRoutes(
    subcategoryData: SubcategoryDataSource
) {
    get("/subcategory") {
        val allSubcategory = subcategoryData.getAllSubcategory()
        call.respond(allSubcategory)
    }

    get("/subcategory/{id}") {
        val id = call.parameters["id"].toString()
        val articleById = subcategoryData.findById(id)
        articleById?.let {
            call.respond(articleById)
        } ?: call.respond(HttpStatusCode.BadRequest)
    }

    post("/subcategory") {
        val request = try {
            call.receive<AddSubcategoryRequest>()
        } catch (e: CannotTransformContentToTypeException) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val areFieldsBlank = request.name.isBlank() || request.path.isBlank()
        if (areFieldsBlank) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val subcategory = Subcategory(
            name = request.name,
            path = request.path,
            card_img = request.image
        )
        val wasAcknowledged = subcategoryData.addSubcategory(subcategory)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        call.respond(HttpStatusCode.OK)
    }

    delete("/subcategory/{id}") {
        val id = call.parameters["id"].toString()

        if (subcategoryData.deleteSubcategoryById(id)) {
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

    put("/subcategory/{id}") {
        val id = call.parameters["id"].toString()

        val subcategory = call.receive<AddSubcategoryRequest>()
        val updatedSuccessfully = subcategoryData.updateSubcategoryById(id, subcategory)
        if (updatedSuccessfully) {
            call.respond(HttpStatusCode.OK, "Article was edited")
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

}