package solutions.CHK

object CheckoutSolution {

    private val priceMapIndividual = mapOf("A" to 50, "B" to 30, "C" to 20, "D" to 15)
    private val specialOffers = mapOf("A" to Pair(3, 130), "B" to Pair(2, 45))
    fun checkout(skus: String): Int {
        var total = 0
        val items =  skus.split(",", " ", "_", "-").filter { it.isNotEmpty() }.groupingBy { it }.eachCount()
        if (items.keys.any { it !in priceMapIndividual.keys }) {
            return -1
        }
        items.forEach { (sku, quantity) ->
            val threshold = specialOffers[sku]?.first ?: 1
            val specialPrice = specialOffers[sku]?.second ?: 0
            val normalPrice = priceMapIndividual[sku] ?:
                throw RuntimeException("We shouldn't get here because we've already checked this")
            total += quantity/threshold * specialPrice
            total += quantity%threshold * normalPrice
        }
        return total
    }
}