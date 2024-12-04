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
        assertEquals(-1, CheckoutSolution.checkout("-"))
    }

    @Test
    fun `2E get B free`() {
        assertEquals(80, CheckoutSolution.checkout("EBE"))
        assertEquals(180, CheckoutSolution.checkout("ABAEE"))
        assertEquals(110, CheckoutSolution.checkout("BBEE"))
        assertEquals(160, CheckoutSolution.checkout("BBEEEE"))
    }

    @Test
    fun `5A is 200 and 4A is 180`() {
        assertEquals(200, CheckoutSolution.checkout("AAAAA"))
        assertEquals(180, CheckoutSolution.checkout("AAAA"))
    }

    @Test
    fun `lots of special offers`() {
        assertEquals(400, CheckoutSolution.checkout("AEEABABAAEEE"))
    }
}