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
        { items: MutableMap<String, Int> ->
            val quantity = items["A"] ?: 0
            val threshold = 5
            val numSpecialDeals = (quantity / threshold)
            val specialPrice = 200
            items["A"] = quantity - (numSpecialDeals * threshold)
            numSpecialDeals * specialPrice
        },
        { items: MutableMap<String, Int> ->
            val quantityE = items["E"] ?: 0
            val quantityB = items["B"] ?: 0
            val threshold = 2
            val numSpecialDeals = (quantityE / threshold) - quantityB
            val specialPrice = 2 * priceMapIndividual["E"]!! - priceMapIndividual["B"]!!
            items["E"] = quantityE - (numSpecialDeals * threshold)
            items["B"] = quantityB - numSpecialDeals
            numSpecialDeals * specialPrice
        },
        { items: MutableMap<String, Int> ->
            val quantity = items["A"] ?: 0
            val threshold = 3
            val numSpecialDeals = (quantity / threshold)
            val specialPrice = 130
            items["A"] = quantity - (numSpecialDeals * threshold)
            numSpecialDeals * specialPrice
        },
        { items: MutableMap<String, Int>  ->
            val quantity = items["B"] ?: 0
            val threshold = 2
            val numSpecialDeals = (quantity / threshold)
            val specialPrice = 45
            items["B"] = quantity - (numSpecialDeals * threshold)
            numSpecialDeals * specialPrice
        },
    )
    fun checkout(skus: String): Int {
        if (skus.any { it.toString() !in priceMapIndividual.keys }) {
            return -1
        }
        var total = 0
        val items =  skus.split("").filter { it.isNotEmpty() }.groupingBy { it }.eachCount().toMutableMap()

        // Calculate special offers first (this will mutate the map!)
        specialOffers.forEach { newPriceFn ->
            total += newPriceFn(items)
        }

        // Calculate what's left at normal price
        items.forEach { (sku, quantity) ->
            val normalPrice = priceMapIndividual[sku] ?:
                throw RuntimeException("We shouldn't get here because we've already checked this")
            total += quantity * normalPrice
        }
        return total
    }
}