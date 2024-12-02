package solutions.CHK

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CheckoutSolutionTest {
    @Test
    fun `working test`() {
        assertEquals(260, CheckoutSolution.checkout("A, B, A, B, A, A, C, D"))
        assertEquals(260, CheckoutSolution.checkout("A B A B A A C D"))
        assertEquals(260, CheckoutSolution.checkout("A_B_A_B_A_A_C_D"))
        assertEquals(260, CheckoutSolution.checkout("""
            A
            B
            A
            B
            A
            A
            C
            D
        """.trimIndent()))
    }

    @Test
    fun `broken call`() {
        assertEquals(-1, CheckoutSolution.checkout("A, B, D, C, FFF"))
    }
}