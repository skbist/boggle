package boggle

import java.util.*

class TrieNode {

    var children = TreeMap<Int,TrieNode>()
    var leaf: Boolean = false

    init {
        leaf = false
    }
}