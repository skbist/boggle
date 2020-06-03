package boggle

import boggle.Constants.Companion.DICTIONARY_FILE_PATH
import java.io.File


class WordDictionary private constructor() {

    companion object {

        private var instance: WordDictionary? = null
        private var root: TrieNode? = null
        val rootNode: TrieNode
            get() {
                if (root == null) {
                    if (instance == null) {
                        instance = WordDictionary()
                    }
                    WordDictionary.instance?.loadWords()
                }
                return root!!
            }
    }

    init {
        loadWords()
    }

    private fun loadWords() {
        root = TrieNode()
        val dictionary: List<String> = File(DICTIONARY_FILE_PATH).readLines()
        for (word in dictionary) {
            root?.let { insert(it, word) }
        }
    }

    private fun insert(root: TrieNode, word: String) {
        val wordLength = word.length
        var pChild = root

        for (i in 0 until wordLength) {
            val index = word[i] - 'a'
            if (pChild.children[index] == null)
                pChild.children.put(index, TrieNode())

            pChild = pChild.children.get(index)!!
        }
        pChild.leaf = true
    }


}
