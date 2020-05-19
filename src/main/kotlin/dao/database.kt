package dao

import dao.GameScores.correctWords
import dao.GameScores.game
import dao.GameScores.player
import dao.GameScores.score
import model.GameType
import model.Score
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


object GameScores : Table() {
    val id = integer("id").autoIncrement()// Column<String>
    val game = varchar("game", length = 40)
    val score = integer("score")
    val player = varchar("player", length = 30)
    val correctWords = text("correctWords").nullable()

    override val primaryKey = PrimaryKey(id)
}

class BoggleRepo {
    init {
        initDatabase()
    }

    private fun initDatabase() {
        Database.connect(
            "jdbc:postgresql://localhost:5432/boggle",
            driver = "org.postgresql.Driver",
            user = "boggle",
            password = "boggleabc"
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
                    gameType = GameType.valueOf(result[game]),
                    score = result[score],
                    player = result[player],
                    words = result[correctWords]
                )
            }
        }
        return insertedScore
    }

    fun getScores(inplayer: String): List<Score> {
        var userScores = mutableListOf<Score>()
        transaction {
            addLogger(StdOutSqlLogger)
            val retData = GameScores.select { GameScores.player eq inplayer }.orderBy(GameScores.id to SortOrder.DESC)
            retData.forEach {
                var sc = Score(
                    gameType = GameType.valueOf(it[game]),
                    score = it[score],
                    player = it[player],
                    words = it[correctWords]
                )
                userScores.add(sc)
            }
        }
        return userScores
    }
}