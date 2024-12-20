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
    fun `2F get F free`() {
        assertEquals(20, CheckoutSolution.checkout("FFF"))
        assertEquals(165, CheckoutSolution.checkout("BAAFFBF"))
    }

    @Test
    fun `lots of special offers`() {
        assertEquals(400, CheckoutSolution.checkout("AEEABABAAEEE"))
        assertEquals(550, CheckoutSolution.checkout("AEEABABAAEEERRRQ"))
        assertEquals(770, CheckoutSolution.checkout("AEEABABAAEEERRRQSPPPPP"))
    }

    @Test
    fun `test for K offers`() {
        assertEquals(120, CheckoutSolution.checkout("KK"))
        assertEquals(190, CheckoutSolution.checkout("KKK"))
        assertEquals(240, CheckoutSolution.checkout("KKKK"))

    }

    @Test
    fun `group discount offers`() {
        assertEquals(45, CheckoutSolution.checkout("SSS"))
        assertEquals(45, CheckoutSolution.checkout("SXY"))
        assertEquals(62, CheckoutSolution.checkout("SXYT"))
        assertEquals(62, CheckoutSolution.checkout("ZXYT"))
    }
}

