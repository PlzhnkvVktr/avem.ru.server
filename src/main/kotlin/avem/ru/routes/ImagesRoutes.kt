package avem.ru.routes

import avem.ru.data.images.ImagesDataSource
import avem.ru.data.model.Image
import avem.ru.requests.AddImageRequest
import avem.ru.response.Response
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import org.bson.types.ObjectId
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

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

    post("/signup") {
        val formParameters = call.receiveParameters()
        val username = formParameters["username"].toString()
        call.respondText("The '$username' account is created")
    }

    post("/images/upload") {
        var fileDescription = ""
        var fileName = ""
        val multipartData = call.receiveMultipart()

        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    fileDescription = part.value
                }

                is PartData.FileItem -> {
                    fileName = part.originalFileName as String
                    val file = if (!Files.exists(Paths.get("static/$fileName"))) {
                        File("static/$fileName")
                    } else {
                        File("static/${System.nanoTime()})_${fileName}")
                    }
                    val fileBytes = part.streamProvider().readBytes()
                    file.writeBytes(fileBytes)


                    val uuID = ObjectId().toString()
                    val image = Image(
                        src = "static/${file.name}",
                        id = uuID
                    )

                    val wasAcknowledged = imageData.addImage(image)
                    if (!wasAcknowledged) {
                        call.respond(HttpStatusCode.Conflict)
                        return@forEachPart
                    }
                }

                else -> {}
            }
            part.dispose()
        }

        call.respondText("$fileDescription is uploaded to 'uploads/$fileName'")
    }

    delete("/images/{id}") {
        val id = call.parameters["id"].toString()
        val image = imageData.findById(id)
        if (image == null) {
            call.respond(HttpStatusCode.NotFound, "Image not found")
            return@delete
        }

        if (imageData.deleteImageById(id)) {
            try {
                Files.delete(Paths.get(image.src))
            } catch (e: IOException) {
                //todo log
                call.respond(HttpStatusCode.Conflict, "Image not deleted")
                return@delete
            }
            call.respond(
                HttpStatusCode.OK,
                Response(true, "Employee successfully deleted")
            )
        } else {
            call.respond(HttpStatusCode.Conflict, "Image not deleted from db")
            return@delete
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