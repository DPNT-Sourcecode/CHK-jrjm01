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

    private val singleItemMultiBuy = { sku: String, threshold: Int, specialPrice: Int, items: MutableMap<String, Int> ->
        val quantity = items[sku] ?: 0
        val numSpecialDeals = (quantity / threshold)
        val newQuantity = quantity - (numSpecialDeals * threshold)
        numSpecialDeals * specialPrice to newQuantity
    }

    private val buySomeGetSomeFree = { skuA: String, quantityA: Int, quantityB: Int, thresholdA: Int ->
        val numSpecialDeals = min(quantityA / thresholdA, quantityB)
        val specialPrice = 2 * priceMapIndividual[skuA]!!
        val newQuantityA = quantityA - (numSpecialDeals * thresholdA)
        val newQuantityB = quantityB - numSpecialDeals
        numSpecialDeals * specialPrice to newQuantityA to newQuantityB
    }
    // These must be ordered by the value they save to get the customer the best price
    // !! is only safe because we will have already checked for values not in the map
    private val specialOffers = listOf(
        // Saves 50
        "A" to 5 to 200 to singleItemMultiBuy,
        // Saves 30 (B price)
        "E" to 2 to 1 to buySomeGetSomeFree,
        // Saves 20
        "A" to 3 to 130 to singleItemMultiBuy,
        // Saves 15
        "B" to 2 to 45 to singleItemMultiBuy,
        // Saves 10
        "F" to 3 to 10 to singleItemMultiBuy,
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


