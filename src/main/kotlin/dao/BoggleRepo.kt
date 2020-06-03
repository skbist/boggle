package dao

import dao.GameScores.correctWords
import dao.GameScores.game
import dao.GameScores.player
import dao.GameScores.score
import model.GameType
import model.Score
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.FileInputStream
import java.util.*

class BoggleRepo {
    init {
        initDatabase()
    }

    private fun initDatabase() {

        val fis = FileInputStream("src/main/resources/dbConfig.properties")
        val dbProps = Properties()
        dbProps.load(fis)

        Database.connect(
            url = dbProps.getProperty("url") ,
            driver = "org.postgresql.Driver",
            user = dbProps.getProperty("user"),
            password = dbProps.getProperty("password")
        )

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.createMissingTablesAndColumns(GameScores)
        }
    }


    fun insertScore(scoreDto: Score): Score? {
        var insertedScore: Score? = null
        transaction {
            addLogger(StdOutSqlLogger)
            var retVal = GameScores.insert {
                it[game] = scoreDto.gameType.toString()
                it[score] = scoreDto.score
                it[player] = scoreDto.player
                it[correctWords] = scoreDto.words
            }
            val result = retVal.resultedValues?.get(0)
            if (result != null) {
                insertedScore = Score(
                    gameType = GameType.valueOf(result[GameScores.game]),
                    score = result[GameScores.score],
                    player = result[GameScores.player],
                    words = result[GameScores.correctWords]
                )
            }
        }
        return insertedScore
    }

    fun retriveScores(inplayer: String): List<Score> {
        var userScores = mutableListOf<Score>()
        transaction {
            addLogger(StdOutSqlLogger)
            val retData = GameScores.select { GameScores.player eq inplayer }.orderBy(GameScores.id to SortOrder.DESC)
            retData.forEach {
                var sc = Score(
                    gameType = GameType.valueOf(it[GameScores.game]),
                    score = it[GameScores.score],
                    player = it[GameScores.player],
                    words = it[GameScores.correctWords]
                )
                userScores.add(sc)
            }
        }
        return userScores
    }

}