package endpoints

import boggle.Boggle
import com.google.gson.Gson
import dto.ScoreDTO
import model.ReqData
import model.ValidationResponse
import org.slf4j.LoggerFactory
import service.BoggleService
import spark.kotlin.Http
import java.time.LocalDateTime
import javax.management.OperationsException

fun gameEndPoints(http: Http, mapper: Gson) {
    val logger = LoggerFactory.getLogger("GameEndpoint")
    val boggleService = BoggleService()

    http.post("/validate") {
        logger.info(LocalDateTime.now().toString(), "received request for generating valid words")
        val inputData = request.body().toString()
        validateInput(inputData)
        val data = mapper.fromJson(inputData, Array<CharArray>::class.java)
        val size = data.size
        validateSize(size)
        val foundWords = Boggle(data.size).findWords(data)
        mapper.toJson(ValidationResponse(words = foundWords.toList()))
    }


    http.post("/saveScores") {
        request.headers()
        val inputData = request.body().toString()
        val inData = mapper.fromJson(inputData, ReqData::class.java)
        validateInput(inputData)
        val gameSize = inData.gameSize;
        validateSize(gameSize)
        val score = ScoreDTO(gameSize = gameSize, player = inData.name, words = inData.wordList)
        val savedScore = boggleService.saveScores(score)

        if (savedScore != null) {
            val scores = boggleService.getScores(savedScore.player)
            mapper.toJson(scores)
        } else {
            throw  OperationsException("Error occured while inserting score")
        }

    }
}

private fun validateSize(size: Int) {
    if (size != 4 && size != 5) {
        throw  IllegalArgumentException(" only 4 square and 5 square game is supported")
    }
}

private fun validateInput(inputData: String) {
    if (inputData.isEmpty()) {
        throw  IllegalArgumentException("Input is not valid re-verify the input")
    }
}

