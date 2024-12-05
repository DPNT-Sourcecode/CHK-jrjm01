package solutions.CHK

import kotlin.math.min
import kotlin.math.roundToInt

object CheckoutSolution {

    private interface SpecialOfferTerms {
        fun totalValueSaved(): Int
        fun calculatePriceAndUpdateItems(items: MutableMap<String, Int>): Int
    }

    private data class MultiBuy(
        val sku: String,
        val threshold: Int,
        val specialPrice: Int,
    ) : SpecialOfferTerms {
        override fun totalValueSaved(): Int {
            val originalPrice = priceMap[sku]!! * threshold
            return originalPrice - specialPrice
        }

        override fun calculatePriceAndUpdateItems(items: MutableMap<String, Int>): Int {
            val quantity = items[sku] ?: 0
            val numSpecialDeals = quantity / threshold
            items[sku] = quantity - (numSpecialDeals * threshold)
            return numSpecialDeals * specialPrice
        }
    }

    private data class GetOneFree(
        val sku: String,
        val threshold: Int,
        val specialPrice: Int,
        val freeItemSku: String
    ) : SpecialOfferTerms {

        override fun totalValueSaved(): Int {
            val originalPrice = priceMap[sku]!! * threshold + priceMap[freeItemSku]!!
            return originalPrice - specialPrice
        }

        override fun calculatePriceAndUpdateItems(items: MutableMap<String, Int>): Int {
            val quantity = items[sku] ?: 0
            val quantityB = items[freeItemSku] ?: 0
            val numSpecialDeals = min(quantity / threshold, quantityB)
            items[sku] = quantity - (numSpecialDeals * threshold)
            items[freeItemSku] = quantityB - numSpecialDeals
            return numSpecialDeals * specialPrice
        }
    }

    // When defining a group discount, order the skus by most expensive to ensure best saving for the customer
    private data class GroupDiscount(
        val skus: List<String>,
        val threshold: Int,
        val specialPrice: Int,
    ) : SpecialOfferTerms {

        override fun totalValueSaved(): Int {
            // In this case it's the average amount saved, which doesn't guarantee the best saving if
            // the skus are involved in other deals but that's not how we're discounting just yet.
            val originalPrice = skus.map { priceMap[it]!! }.average().roundToInt() * threshold
            return originalPrice - specialPrice
        }

        override fun calculatePriceAndUpdateItems(items: MutableMap<String, Int>): Int {

            val offerItems = items.filter { it.key in skus }
            val orderedItems = offerItems.toSortedMap(compareBy { skus.indexOf(it) }).flatMap { it.value.take "$it.key".repeat(it.value) }
            val quantity = offerItems.values.sumOf { it }
            val numSpecialDeals = quantity / threshold
            repeat(numSpecialDeals * threshold) { index ->
                val sku = orderedItems[index].first
                items[sku]?.dec()
            }
            return numSpecialDeals * specialPrice
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
        MultiBuy("A", 5, 200),
        MultiBuy("A", 3, 130),
        MultiBuy("B", 2, 45),
        GetOneFree("E", 2, 80, "B"),
        MultiBuy("F", 3, 20),
        MultiBuy("H", 5, 45),
        MultiBuy("H", 10, 80),
        MultiBuy("K", 2, 150),
        GetOneFree("N", 3, 120, "M"),
        MultiBuy("P", 5, 200),
        MultiBuy("Q", 3, 80),
        GetOneFree("R", 3, 150, "Q"),
        MultiBuy("U", 4, 120),
        MultiBuy("V", 2, 90),
        MultiBuy("V", 3, 130),
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
        specialOffers.forEach { offer ->
            total += offer.calculatePriceAndUpdateItems(items)
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





