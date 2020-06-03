import com.google.gson.Gson
import endpoints.gameEndPoints
import exceptions.handleException
import org.slf4j.LoggerFactory
import spark.Spark.exception
import spark.Spark.initExceptionHandler
import spark.kotlin.*
import java.io.FileInputStream
import java.util.*

fun main(args: Array<String>) {

    val fis = FileInputStream("src/main/resources/server.properties")
    val serverProps = Properties()
    serverProps.load(fis)
    val port = Integer.parseInt(serverProps.getProperty("port"))

    val http: Http = ignite().port(port).ipAddress(serverProps.getProperty("ipAddress"))

    val logger = LoggerFactory.getLogger("Server")
    var mapper = Gson()


    initExceptionHandler { e -> logger.error("ignite failed", e) }

    fun serverError(code: Int = 500, message: String = "Internal server error"): String {
        return mapper.toJson(
            mapOf(
                "success" to false,
                "status" to code,
                "msg" to message
            )
        )
    }


    notFound {
        type("application/json")
        status(404)
        serverError(404, "page not found,")
    }

    internalServerError {
        type("application/json")
        status(500)
        serverError()
    }


    http.before("*") {
        response.header("Access-Control-Allow-Origin", "*")
        response.header("Access-Control-Request-Method", "*")
        response.header("Access-Control-Allow-Headers", "*")
        response.type("application/json")
    }

    gameEndPoints(http, mapper)

    exception(Exception::class.java) { e, _, res ->
        res.type("application/json")
        val responseMap = handleException(e)
        res.status(responseMap["status"] as Int)
        res.body(mapper.toJson(responseMap))
    }

}