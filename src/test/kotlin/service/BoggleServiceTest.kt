package service

import dao.BoggleRepo
import dto.ScoreDTO
import model.GameType
import model.Score
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class BoggleServiceTest {

    @Test
    fun saveScores() {
        val scoreDto = ScoreDTO(5, player = "user1", words = arrayOf("hello", "welcome", "boggle"))
        val score = Score(GameType.FIVE_SQUARE, player = "user1", score = 17, words = "hello, welcome, boggle")
        val boggleRepo: BoggleRepo = Mockito.mock(BoggleRepo::class.java)
        Mockito.`when`(boggleRepo.insertScore(score)).thenReturn(score)
        val boggleService = BoggleService(boggleRepo)
        var expectedResult = boggleService.saveScores(scoreDto)
        assertEquals(expectedResult, boggleService.saveScores(scoreDto))
    }


    @Test
    fun getScores() {
        val score1 = Score(GameType.FIVE_SQUARE, player = "user1", score = 22, words = "hello, welcome, play, boggle")
        val score2 = Score(GameType.FIVE_SQUARE, player = "user1", score = 17, words = "hello, welcome, boggle")
        var scores: List<Score> = listOf<Score>(score1, score2)
        val boggleRepo: BoggleRepo = Mockito.mock(BoggleRepo::class.java)
        Mockito.`when`(boggleRepo.retriveScores("user1")).thenReturn(scores)
        val boggleService = BoggleService(boggleRepo)
        var expectedResult = boggleService.getScores("user1").size
        assertEquals(expectedResult, boggleService.getScores("user1").size)
    }

}