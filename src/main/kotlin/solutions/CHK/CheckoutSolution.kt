package solutions.CHK

object CheckoutSolution {

    private val priceMapIndividual = mapOf(
        "A" to 50,
        "B" to 30,
        "C" to 20,
        "D" to 15,
        "E" to 40,
    )
    private val specialOffers = mapOf(
        "A" to Pair(3, 130),
        "B" to Pair(2, 45),
        "E" to Pair(2, 2 * priceMapIndividual["E"]!! - priceMapIndividual["B"]!!) // !! is only safe because we will have already checked for values not in the map
    )
    fun checkout(skus: String): Int {
        if (skus.any { it.toString() !in priceMapIndividual.keys }) {
            return -1
        }
        var total = 0
        val items =  skus.split("").filter { it.isNotEmpty() }.groupingBy { it }.eachCount()
        items.forEach { (sku, quantity) ->
            val threshold = specialOffers[sku]?.first ?: 0
            val specialPrice = specialOffers[sku]?.second ?: 0
            val normalPrice = priceMapIndividual[sku] ?:
                throw RuntimeException("We shouldn't get here because we've already checked this")
            val numSpecialDeals = if (threshold == 0) {
                0
            } else {
                quantity / threshold
            }
            val numNonSpecialItems = quantity - (numSpecialDeals * threshold)
            total += numSpecialDeals * specialPrice
            total += numNonSpecialItems * normalPrice
        }
        return total
    }
}

