package avem.ru.routes

import avem.ru.data.images.ImagesDataSource
import avem.ru.data.model.Image
import avem.ru.requests.AddImageRequest
import avem.ru.response.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import org.bson.types.ObjectId
import java.io.File
import java.util.UUID

fun Route.getImageRoutes(
    imageData: ImagesDataSource
) {
    get("/images") {
        val allImages = imageData.getAllImages()
        call.respond(allImages)
    }

    get("/images/{id}") {
        val id = call.parameters["id"].toString()
        val articleById = imageData.findById(id)
        articleById?.let {
            call.respond(articleById)
        } ?: call.respond(HttpStatusCode.BadRequest)
    }

    post("/images") {
        val request = try {
            call.receive<AddImageRequest>()
        } catch (e: CannotTransformContentToTypeException) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val areFieldsBlank = request.src.isBlank()
        if (areFieldsBlank) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val image = Image(
            src = request.src,
        )
        val wasAcknowledged = imageData.addImage(image)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        call.respond(HttpStatusCode.OK)
    }

    post("/images/upload") {
        val uuID = ObjectId().toString()
        val imgPath = "assets/$uuID.jpg"
        val file = File(imgPath)
        file.writeBytes(call.receiveStream().readAllBytes())
        val image = Image(
            src = imgPath,
            id = uuID
        )
        val wasAcknowledged = imageData.addImage(image)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
        call.respond(image)
    }

    delete("/images/{id}") {

        val id = call.parameters["id"].toString()

        if (imageData.deleteImageById(id)) {
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

    put("/images/{id}") {
        val id = call.parameters["id"].toString()

        val image = call.receive<AddImageRequest>()
        val updatedSuccessfully = imageData.updateImageById(id, image)
        if (updatedSuccessfully) {
            call.respond(HttpStatusCode.OK, "Article was edited")
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

}