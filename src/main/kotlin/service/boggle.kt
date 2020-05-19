package service

import com.sun.javaws.exceptions.InvalidArgumentException
import java.io.File

class Boggle(count: Int) {
    var words = mutableSetOf<String>()
    val SIZE = 26
    val DICTIONARY_FILE_PATH = "src/main/resources/words.txt";
    var ROWS_COUNT: Int = count
    var COL_COUNT: Int = count


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

    class TrieNod {
        val SIZE = 26
        var childNodes = arrayOfNulls<TrieNod>(SIZE)
        var leaf: Boolean = false

        init {
            leaf = false
            for (i in 0 until SIZE)
                childNodes[i] = null
        }
    }

    fun wordfinder(boardData: Array<CharArray>): Set<String> {
        val root = TrieNod()
        var dictionary: List<String> = mutableListOf()
        dictionary = File(DICTIONARY_FILE_PATH).readLines()
        for (word in dictionary) {
            insert(root, word)
        }
        return findWords(boardData, root)
    }

    private fun insert(root: TrieNod, Key: String) {
        val n = Key.length
        var pChild = root

        for (i in 0 until n) {
            val index = Key[i] - 'a'

            if (pChild.childNodes[index] == null)
                pChild.childNodes[index] = TrieNod()

            pChild = pChild.childNodes[index]!!
        }
        pChild.leaf = true
    }

    private fun isSafe(i: Int, j: Int, visited: Array<BooleanArray>): Boolean {
        return i in 0..(ROWS_COUNT - 1) && j in 0..(COL_COUNT - 1) && !visited[i][j]
    }

    private fun searchWord(
        root: TrieNod, boggle: Array<CharArray>, i: Int,
        j: Int, visited: Array<BooleanArray>, str: String
    ) {

        if (root.leaf) {
            if (str.length > 2) {
                this.words.add(str)
            }
        }
        if (isSafe(i, j, visited)) {
            visited[i][j] = true
            for (K in 0 until SIZE) {
                if (root.childNodes[K] != null) {
                    val ch = (K + 'a'.toInt()).toChar()
                    ADJACENT_POSITIONS.forEach {
                        val ithIndex = i + (it[0])
                        val jthIndex = j + (it[1])
                        if (isSafe(ithIndex, jthIndex, visited) && boggle[ithIndex][jthIndex] == ch)
                            searchWord(
                                root.childNodes[K]!!, boggle, ithIndex, jthIndex,
                                visited, str + ch
                            )
                    }
                }
            }
            visited[i][j] = false
        }
    }

    @Throws(InvalidArgumentException::class)
    private fun findWords(boggle: Array<CharArray>, root: TrieNod): Set<String> {
        // Mark all characters as not visited
        val visited = Array(ROWS_COUNT) { BooleanArray(COL_COUNT) }

        var str = ""
        for (i in 0 until ROWS_COUNT) {
            if (boggle[i].size != ROWS_COUNT) {
                throw IllegalArgumentException("column and rows counts must be equal ${boggle[i]}")
            }
        }
        for (i in 0 until ROWS_COUNT) {
            for (j in 0 until COL_COUNT) {
                if (root.childNodes[boggle[i][j] - 'a'] != null) {
                    str += boggle[i][j]
                    searchWord(
                        root.childNodes[boggle[i][j] - 'a']!!,
                        boggle, i, j, visited, str
                    )
                    str = ""
                }
            }
        }
        return words
    }
}