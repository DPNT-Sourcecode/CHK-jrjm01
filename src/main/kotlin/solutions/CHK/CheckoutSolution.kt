package solutions.CHK

object CheckoutSolution {

    private val priceMapIndividual = mapOf(
        "A" to 50,
        "B" to 30,
        "C" to 20,
        "D" to 15,
        "E" to 40,
    )

    // These must be ordered by the value they save to get the customer the best price
    // !! is only safe because we will have already checked for values not in the map
    private val specialOffers = listOf(
        { items: Map<String, Int> ->
            val quantity = items["A"]!!
            val threshold = 5
            val numSpecialDeals = (quantity / threshold)
            val specialPrice = 200
            numSpecialDeals * specialPrice to items
        },
        { items: Map<String, Int> ->
            val quantityE = items["E"]!!
            val quantityB = items["E"]!!
            val threshold = 2
            val numSpecialDeals = (quantityE / threshold) - quantityB
            val specialPrice = 2 * priceMapIndividual["E"]!! - priceMapIndividual["B"]!!
            items["E"]!!.dec()
            items["E"]!!.dec()
            items["B"]!!.dec()
            numSpecialDeals * specialPrice to items
        },
        { items: Map<String, Int> ->
            val quantity = items["A"]!!
            val threshold = 3
            val numSpecialDeals = (quantity / threshold)
            val specialPrice = 130
            numSpecialDeals * specialPrice to items
        },
        { items: Map<String, Int>  ->
            val quantity = items["A"]!!
            val threshold = 2
            val numSpecialDeals = (quantity / threshold)
            val specialPrice = 45
            numSpecialDeals * specialPrice to items
        },
    )
    fun checkout(skus: String): Int {
        if (skus.any { it.toString() !in priceMapIndividual.keys }) {
            return -1
        }
        var total = 0
        val items =  skus.split("").filter { it.isNotEmpty() }.groupingBy { it }.eachCount()

        // Calculate special offers first
        specialOffers.forEach { sku, newPriceFn ->

        }

        // Calculate what's left at normal price
        items.forEach { (sku, quantity) ->

            val threshold = specialOffers[sku]?.first ?: 0
            val specialPrice = specialOffers[sku]?.second?.invoke(items) ?: 0
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

