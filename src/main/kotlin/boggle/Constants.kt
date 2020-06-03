package boggle

class Constants{
    companion object {

        val DICTIONARY_FILE_PATH = "src/main/resources/words.txt";

        val ADJACENT_POSITIONS: Array<IntArray> =
            arrayOf(
                intArrayOf(1, 1),
                intArrayOf(0, 1),
                intArrayOf(-1, 1),
                intArrayOf(1, 0),
                intArrayOf(1, -1),
                intArrayOf(0, -1),
                intArrayOf(-1, -1),
                intArrayOf(-1, 0)
            )
    }
}