package boggle

import boggle.Constants.Companion.ADJACENT_POSITIONS
import com.sun.javaws.exceptions.InvalidArgumentException


class Boggle(count: Int) {
    var words = mutableSetOf<String>()
    var ROWS_COUNT: Int = count
    var COL_COUNT: Int = count

    private fun isSafe(row: Int, column: Int, visited: Array<BooleanArray>): Boolean {
        return row in 0..(ROWS_COUNT - 1) && column in 0..(COL_COUNT - 1) && !visited[row][column]
    }

    private fun searchWord(
        root: TrieNode, boggle: Array<CharArray>, row: Int,
        column: Int, visited: Array<BooleanArray>, str: String
    ) {

        if (root.leaf && str.length > 2) {
            this.words.add(str)
        }

        if (isSafe(row, column, visited)) {
            visited[row][column] = true
            root.children.keys.forEach {
                val child = it
                val ch = (it + 'a'.toInt()).toChar()
                ADJACENT_POSITIONS.forEach {
                    val rowIndex = row + (it[0])
                    val colIndex = column + (it[1])
                    if (isSafe(
                            rowIndex,
                            colIndex,
                            visited
                        ) && boggle[rowIndex][colIndex] == ch
                    )//if trie child is present in boggle
                        searchWord(
                            root.children.get(child)!!, boggle, rowIndex, colIndex,
                            visited, str + ch
                        )
                }
            }
            visited[row][column] = false
        }
    }

    @Throws(InvalidArgumentException::class)
    fun findWords(boggle: Array<CharArray>): Set<String> {
        val root = WordDictionary.rootNode
        val visited = Array(ROWS_COUNT) { BooleanArray(COL_COUNT) }  // Mark all characters as not visited
        var str = ""
        for (row in 0 until ROWS_COUNT) {
            if (boggle[row].size != ROWS_COUNT) {
                throw IllegalArgumentException("column and rows counts must be equal ${boggle[row]}")
            }
            for (column in 0 until COL_COUNT) {
                (root.children[boggle[row][column] - 'a']).let {
                    //if board item is present as first child of tie root
                    str += boggle[row][column]
                    searchWord(
                        root.children[boggle[row][column] - 'a']!!,
                        boggle, row, column, visited, str
                    )
                    str = ""
                }
            }
        }
        return words
    }


}


