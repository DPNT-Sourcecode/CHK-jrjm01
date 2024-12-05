package solutions.CHK

import kotlin.math.min

object CheckoutSolution {

    private data class SpecialOfferTerms(
        val sku: String,
        val threshold: Int,
        val specialPrice: Int,
        val freeItem: String? = null
    ) {
        fun totalValueSaved(): Int {
            val originalPrice =
                (priceMap[sku]!! * threshold) +
                        (freeItem?.let { priceMap[freeItem]!! } ?: 0)
            return originalPrice - specialPrice
        }
    }

    private val priceMap = mapOf(
        "A" to 50,
        "B" to 30,
        "C" to 20,
        "D" to 15,
        "E" to 40,
        "F" to 10,
        "G" to 20,
        "H" to 10,
        "I" to 35,
        "J" to 60,
        "K" to 80,
        "L" to 90,
        "M" to 15,
        "N" to 40,
        "O" to 10,
        "P" to 50,
        "Q" to 30,
        "R" to 50,
        "S" to 30,
        "T" to 20,
        "U" to 40,
        "V" to 50,
        "W" to 20,
        "X" to 90,
        "Y" to 10,
        "Z" to 50,
    )

    // These must be ordered by the value they save to get the customer the best price
    // !! is only safe because we will have already checked for values not in the map
    private val specialOffers = listOf(
        SpecialOfferTerms("A", 5, 200),
        SpecialOfferTerms("A", 3, 130),
        SpecialOfferTerms("B", 2, 45),
        SpecialOfferTerms("E", 2, 80, "B"),
        SpecialOfferTerms("F", 3, 20),
        SpecialOfferTerms("H", 5, 45),
        SpecialOfferTerms("H", 10, 80),
        SpecialOfferTerms("K", 2, 150),
        SpecialOfferTerms("N", 3, 120, "M"),
        SpecialOfferTerms("P", 5, 200),
        SpecialOfferTerms("Q", 3, 80),
        SpecialOfferTerms("R", 3, 150, "Q"),
        SpecialOfferTerms("U", 4, 120),
        SpecialOfferTerms("V", 2, 90),
        SpecialOfferTerms("V", 3, 130),
    ).sortedByDescending { it.totalValueSaved() }

    private val specialOfferPrice = { sku: String,
                                      threshold: Int,
                                      specialPrice: Int,
                                      skuB: String?,
                                      items: MutableMap<String, Int> ->
        val quantity = items[sku] ?: 0
        val quantityB = skuB?.let { items[skuB] ?: 0 } ?: Int.MAX_VALUE
        val numSpecialDeals = min(quantity / threshold, quantityB)
        items[sku] = quantity - (numSpecialDeals * threshold)
        skuB?.let { items[skuB] = quantityB - numSpecialDeals }
        numSpecialDeals * specialPrice
    }

    fun checkout(skus: String): Int {
        if (skus.any { it.toString() !in priceMap.keys }) {
            return -1
        }
        var total = 0
        val items = skus.split("").filter { it.isNotEmpty() }.groupingBy { it }.eachCount().toMutableMap()

        // Calculate special offers first (this will mutate the map!)
        specialOffers.forEach { (sku, threshold, specialPrice, skuB) ->
            total += specialOfferPrice(sku, threshold, specialPrice, skuB, items)
        }

        // Calculate what's left at normal price
        items.forEach { (sku, quantity) ->
            val normalPrice = priceMap[sku]
                ?: throw RuntimeException("We shouldn't get here because we've already checked this")
            total += quantity * normalPrice
        }
        return total
    }
}