package service

import dao.BoggleRepo
import dao.GameScores.score
import dto.ScoreDTO
import model.GameType
import model.Score

class BoggleService(var boggleRepo:BoggleRepo = BoggleRepo()){

    fun saveScores(scoreDTO: ScoreDTO): Score? {
        val gameType = if (scoreDTO.gameSize == 4) GameType.FOUR_SQUARE else GameType.FIVE_SQUARE
        var scoreCount = 0
        scoreDTO.words?.forEach {
            scoreCount += it.length
        }
        if(scoreDTO.words?.size?:0 <1){
            throw  IllegalArgumentException("please enter words and then submit")
        }
        val correctWords = scoreDTO.words?.joinToString(separator = ", ")

        val score = Score(gameType = gameType, score = scoreCount, player = scoreDTO.player, words = correctWords)
        return boggleRepo.insertScore(scoreDto = score)
    }

    fun getScores(player:String): List<Score> {
        return boggleRepo.retriveScores(inplayer = player)
    }
}