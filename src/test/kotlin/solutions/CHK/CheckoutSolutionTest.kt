package solutions.CHK

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CheckoutSolutionTest {
    @Test
    fun `working test`() {
        assertEquals(260, CheckoutSolution.checkout("ABABAACD"))
    }

    @Test
    fun `broken call`() {
        assertEquals(-1, CheckoutSolution.checkout("ABDCFFF"))
    }
}