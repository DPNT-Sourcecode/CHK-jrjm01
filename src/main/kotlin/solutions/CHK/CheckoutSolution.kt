package solutions.CHK

import kotlin.math.max
import kotlin.math.min

object CheckoutSolution {

    private val priceMapIndividual = mapOf(
        "A" to 50,
        "B" to 30,
        "C" to 20,
        "D" to 15,
        "E" to 40,
        "F" to 10,
    )

    private val singleItemMultiBuy = { sku: String,
                                       threshold: Int,
                                       specialPrice: Int,
                                       skuB: String?,
                                       quantityB: Int?,
                                       items: MutableMap<String, Int> ->
        val quantity = items[sku] ?: 0
        val numSpecialDeals = min(quantity / threshold, quantityB ?: 0)
        items[sku] = quantity - (numSpecialDeals * threshold)
        if (skuB != null) {
            val quantityB = items[skuB] ?: 0
            items[skuB] = quantityB - numSpecialDeals
        }
        numSpecialDeals * specialPrice
    }

//    private val buySomeGetSomeFree = { skuA: String, quantityA: Int, quantityB: Int?, thresholdA: Int ->
//        val numSpecialDeals = min(quantityA / thresholdA, quantityB)
//        val specialPrice = thresholdA * priceMapIndividual[skuA]!!
//        val newQuantityA = quantityA - (numSpecialDeals * thresholdA)
//        val newQuantityB = quantityB - numSpecialDeals
//        numSpecialDeals * specialPrice to newQuantityA to newQuantityB
//    }

    private data class SpecialOfferTerms(
        val sku: String,
        val threshold: Int,
        val specialPrice: Int,
        val freeItem: String? = null
    )
    // These must be ordered by the value they save to get the customer the best price
    // !! is only safe because we will have already checked for values not in the map
    private val specialOffers = listOf(
        // Saves 50
        SpecialOfferTerms("A", 5, 200),
        // Saves 30 (B price)
        SpecialOfferTerms("E", 2, 80, "B"),
        // Saves 20
        SpecialOfferTerms("A", 3, 130),
        // Saves 15
        SpecialOfferTerms("B", 2, 45),
        // Saves 10
        SpecialOfferTerms("F", 3, 10),
    )

    fun checkout(skus: String): Int {
        if (skus.any { it.toString() !in priceMapIndividual.keys }) {
            return -1
        }
        var total = 0
        val items =  skus.split("").filter { it.isNotEmpty() }.groupingBy { it }.eachCount().toMutableMap()

        // Calculate special offers first (this will mutate the map!)
        specialOffers.forEach { (sku, threshold, specialPrice, skuB) ->
            total += singleItemMultiBuy(sku, threshold, specialPrice, skuB)
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
