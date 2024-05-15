package avem.ru.routes

import avem.ru.data.model.Product
import avem.ru.data.products.ProductsDataSource
import avem.ru.requests.AddProductRequest
import avem.ru.response.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getProductRoutes(
    productsData: ProductsDataSource
){
    get("products"){
        val allProducts = productsData.getAllProducts()
        call.respond(allProducts)
    }

    get("products/{id}") {
        val id = call.parameters["id"].toString()
        val response = productsData.findById(id)
        response?.let {
            call.respond(response)
        } ?: call.respond(HttpStatusCode.BadRequest)
    }

    get("products/category/{category}") {
        val category = call.parameters["category"].toString().toInt()
        val response = productsData.getProductsByCategory(category)
        response?.let {
            call.respond(response)
        } ?: call.respond(HttpStatusCode.BadRequest)
    }

    get("products/subcategory/{subcategory}") {
        val subcategory = call.parameters["subcategory"].toString().toInt()
        val response = productsData.getProductsBySubcategory(subcategory)
        response?.let {
            call.respond(response)
        } ?: call.respond(HttpStatusCode.BadRequest)
    }

    post("products"){
        val request = call.receiveOrNull<AddProductRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val areFieldsBlank = request.name.isBlank()
        if (areFieldsBlank) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val product = Product(
            name = request.name,
            card_img = request.card_img,
            description = request.description,
            characteristic = request.characteristic,
            specification = request.specification,
            additionally = request.additionally,
            category = request.category,
            subcategory = request.subcategory,
            images = request.images
        )
        val wasAcknowledged = productsData.addProduct(product)
        if(!wasAcknowledged)  {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        call.respond(HttpStatusCode.OK)
    }

    delete("products/{id}"){

        val id = call.parameters["id"].toString()

        if (productsData.deleteProductForId(id)) {
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

    put("products/{id}") {

        val id = call.parameters["id"].toString()

        val product = call.receive<AddProductRequest>()
        val updatedSuccessfully = productsData.updateProductById(id, product)
        if (updatedSuccessfully) {
            call.respond(HttpStatusCode.OK, "Article was edited")
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}