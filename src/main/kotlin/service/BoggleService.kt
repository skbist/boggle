package service

import dao.BoggleRepo
import model.Score

class BoggleService(var boggleRepo:BoggleRepo = BoggleRepo()){

    fun saveScores(score:Score): Score? {
       return boggleRepo.insertScore(scoreDto = score)
    }

    fun getScores(player:String): List<Score> {
        return boggleRepo.getScores(inplayer = player)
    }
}