// run with kotlin petstore.main.kts

@file:DependsOn("io.ktor:ktor-client-okhttp:+")
@file:DependsOn("io.ktor:ktor-client-gson:+")

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking

data class InventoryResponse(val sold: Int, val pending: Int, val available: Int)

val url = "https://petstore.swagger.io/v2/store/inventory"
HttpClient { install(JsonFeature) }.use { client ->
    runBlocking {
        val (sold, pending, available) = client.get<InventoryResponse>(url)
        println("Result: Sold $sold, pending $pending, available $available")
    }
}
