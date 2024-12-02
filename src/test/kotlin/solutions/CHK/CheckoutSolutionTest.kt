package solutions.CHK

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CheckoutSolutionTest {
    @Test
    fun `working test`() {
        CheckoutSolution.checkout("A, B, A, B, A, A, C, D")
    }
}