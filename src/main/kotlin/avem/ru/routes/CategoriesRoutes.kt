package avem.ru.routes

import avem.ru.data.categoty.CategoryDataSource
import avem.ru.data.model.Category
import avem.ru.requests.AddCategoryRequest
import avem.ru.response.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getCategoryRoutes(
    categoryData: CategoryDataSource
) {
    get("/categories") {
        val allCategory = categoryData.getAllCategories()
        call.respond(allCategory)
    }

    get("/categories/{id}") {
        val id = call.parameters["id"].toString()
        val articleById = categoryData.findById(id)
        articleById?.let {
            call.respond(articleById)
        } ?: call.respond(HttpStatusCode.BadRequest)
    }

    post("/categories") {
        val request = try {
            call.receive<AddCategoryRequest>()
        } catch (e: CannotTransformContentToTypeException) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val areFieldsBlank = request.name.isBlank() || request.path.isBlank()
        if (areFieldsBlank) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val category = Category(
            name = request.name,
            path = request.path,
            subcategories = listOf()
        )
        val wasAcknowledged = categoryData.addCategory(category)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        call.respond(HttpStatusCode.OK)
    }

    delete("/categories/{id}") {
        val id = call.parameters["id"].toString()

        if (categoryData.deleteCategoryById(id)) {
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

    put("/categories/{id}") {
        val id = call.parameters["id"].toString()

        val category = call.receive<AddCategoryRequest>()
        val updatedSuccessfully = categoryData.updateCategoryById(id, category)
        if (updatedSuccessfully) {
            call.respond(HttpStatusCode.OK, "Article was edited")
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

}