import com.google.gson.Gson
import dao.GameScores.correctWords
import model.GameType
import model.ReqData
import model.Score
import model.ValidationResponse
import org.slf4j.LoggerFactory
import service.Boggle
import service.BoggleService
import spark.Spark.exception
import spark.Spark.initExceptionHandler
import spark.kotlin.*
import java.io.IOException
import java.time.LocalDateTime
import javax.management.OperationsException

fun main(args: Array<String>) {
    val http: Http = ignite().port(2222)

    ipAddress("localhost")
    var logger = LoggerFactory.getLogger("Server")
    http.options("/*") {
        val accessControlRequestHeaders = request.headers("Access-Control-Request-Headers")
        if (accessControlRequestHeaders != null) {
            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders)
        }
        val accessControlRequestMethod = request.headers("Access-Control-Request-Method")
        if (accessControlRequestMethod != null) {
            response.header("Access-Control-Allow-Methods", accessControlRequestMethod)
        }
        "OK"
    }

    http.before("*") {

        response.header("Access-Control-Allow-Origin", "*")
        response.header("Access-Control-Request-Method", "*")
        response.header("Access-Control-Allow-Headers", "*")
        response.type("application/json")
    }

    var boggleService = BoggleService()

    var mapper = Gson()
    http.post("/validate") {
        logger.info(LocalDateTime.now().toString() , "received request for generating valid words")
        var inputData = request.body().toString()
        var data = mapper.fromJson(inputData, Array<CharArray>::class.java)
        if (inputData.isNullOrEmpty()) {
            logger.error(LocalDateTime.now().toString() , "Input is not valid may be null")
            throw  IllegalArgumentException("Input is not valid re-verify the input")
        }
        val size = data.size
        if (size != 4 && size != 5) {
            throw  IllegalArgumentException(" only 4 square and 5 square game is supported")
        }
        var foundWords = Boggle(data.size).wordfinder(data)
        mapper.toJson(ValidationResponse(words = foundWords.toList()))
    }


    http.post("/saveScores") {
        request.headers()
        var inputData = request.body().toString()
        var inData = mapper.fromJson(inputData, ReqData::class.java)
        if (inputData.isNullOrEmpty()) {
//            "throing exception since parameter is null"
            throw  IllegalArgumentException("Input is not valid re-verify the input")
        }
        val gameSize = inData.gameSize;
        if (gameSize != 4 && gameSize != 5) {
            throw  IllegalArgumentException(" only 4 square and 5 square game is supported")
        }

        var gameType = if (inData.gameSize == 4) GameType.FOUR_SQUARE else GameType.FIVE_SQUARE
        var scoreCount = 0
        inData.wordList?.forEach {
            scoreCount += it.length
        }
        if(inData.wordList?.size?:0 <1){
            throw  IllegalArgumentException("please enter words and then submit")
        }
        var correctWords = inData.wordList?.joinToString(separator = ", ")

        var score = Score(gameType = gameType, score = scoreCount, player = inData.name, words = correctWords)
        val savedScore = boggleService.saveScores(score)

        if (savedScore != null) {
            val scores = boggleService.getScores(savedScore.player)
          mapper.toJson(scores)
        }else {
            throw  OperationsException("Error occured while inserting score")
        }

    }

    fun serverError(code: Int = 500, message: String = "Internal server error"): String {
        return mapper.toJson(
            mapOf(
                "success" to false,
                "status" to code,
                "msg" to message
            )
        )
    }

    initExceptionHandler { e -> logger.error("ignite failed", e) }

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

    fun exceptionHandle(e: Exception): Map<String, Any> {
        // e.printStackTrace()
        val responseMsg: String
        var code = 500
        when (e) {
            is NullPointerException -> {
                logger.error(LocalDateTime.now().toString() + " - You stumbled upon void", e.message)
                responseMsg = "Null pointer exception"
            }
            is NumberFormatException -> {
                logger.error(LocalDateTime.now().toString() + " - Illegal Argument Exception", e.message)
                responseMsg = "Illegal Argument Exception"
            }
            is RuntimeException -> {
                logger.error(LocalDateTime.now().toString() + " - Unknown Runtime Exception", e.message)
                responseMsg = e.message ?: "Unknown Runtime Exception"
            }
            is IOException -> {
                logger.error(LocalDateTime.now().toString() + " - File read IOException", e.message)
                responseMsg = e.message ?: "file read difficulty IOException"
            }
            is IllegalArgumentException -> {
                logger.error(LocalDateTime.now().toString() + "Argument mismatch Exception", e.message)
                responseMsg = e.message ?: "Argument mismatch Exception"
            }
            is OperationsException -> {
                logger.error(LocalDateTime.now().toString() + "Exception Occured during database operation", e.message)
                responseMsg = e.message ?: "Exception Occured during database operation"
            }
            else -> {
                logger.error(LocalDateTime.now().toString() + " - Unknown Error", e.message)
                responseMsg = "Unknown Error"
            }
        }
        return mapOf(
            "success" to false,
            "status" to code,
            "msg" to responseMsg
        )
    }

    exception(Exception::class.java) { e, _, res ->
        res.type("application/json")
        val responseMap = exceptionHandle(e)
        res.status(responseMap["status"] as Int)
        res.body(mapper.toJson(responseMap))
    }

}