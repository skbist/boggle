package service

import boggle.Boggle
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Assertions



internal class BoggleTest {

    @Test
    fun singleWordFound() {
        val data = arrayOf(
            charArrayOf('x', 't', 'r','n'),
            charArrayOf('q', 'h', 'b', 'g'),
            charArrayOf('h', 'e', 'k', 'p'),
            charArrayOf('a', 'q', 'v', 'b')
        )
        val boggle = Boggle(4)
        assertEquals(2,boggle.findWords(data).size ,"should contain only one word")

    }

    @Test
    fun multipleWordFound() {
        val data = arrayOf(
            charArrayOf('o', 'x', 'g','l'),
            charArrayOf('s', 'a', 'u','j'),
            charArrayOf('x', 'g', 'l', 'd'),
            charArrayOf('j', 'b', 'e', 't')
        )
        val boggle = Boggle(4)
        assertEquals(32,boggle.findWords(data).size ,"should contain multiple one word")
    }

    @Test
    fun noWordFound() {
        val data = arrayOf(
            charArrayOf('r', 'm', 'j','x'),
            charArrayOf('v', 'b', 'l','h'),
            charArrayOf('r', 'm', 'j', 'x'),
            charArrayOf('u', 'r', 'm', 'j')
        )
        val boggle = Boggle(4)
        assertEquals(0,boggle.findWords(data).size ,"does not contain Word")
    }


    @Test
    fun unequalLengthOfColumnAndRows() {
        val data = arrayOf(
            charArrayOf('r', 'm','x'),
            charArrayOf('v', 'b', 'h','j'),
            charArrayOf('r', 'm', 'j', 'x'),
            charArrayOf('u', 'r', 'm', 'j')
        )
        val boggle = Boggle(4)
        Assertions.assertThrows(IllegalArgumentException::class.java) { boggle.findWords(data) }
    }
}