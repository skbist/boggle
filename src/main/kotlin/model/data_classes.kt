package model

data class ValidationResponse(var words: List<String>)

data class ReqData(val name: String, val wordList: Array<String>?, val gameSize: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ReqData
        if (name != other.name) return false
        if (wordList != null) {
            if (other.wordList == null) return false
            if (!wordList.contentEquals(other.wordList)) return false
        } else if (other.wordList != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (wordList?.contentHashCode() ?: 0)
        return result
    }
}

data class Score(val gameType: GameType, val score: Int, val player: String, val words: String?)

enum class GameType {
    FOUR_SQUARE,
    FIVE_SQUARE

}
