package dao

import org.jetbrains.exposed.sql.Table

object GameScores : Table() {
    val id = integer("id").autoIncrement()// Column<String>
    val game = varchar("game", length = 40)
    val score = integer("score")
    val player = varchar("player", length = 30)
    val correctWords = text("correctWords").nullable()

    override val primaryKey = PrimaryKey(id)
}