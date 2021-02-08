// run with kotlin petstore.main.kts

@file:DependsOn("com.squareup.retrofit2:retrofit:+")
@file:DependsOn("com.squareup.retrofit2:converter-gson:+")
@file:DependsOn("com.squareup.retrofit2:adapter-rxjava2:+")
@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")

import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.system.exitProcess

val BASE_URL = "https://petstore.swagger.io/v2/"

val client = OkHttpClient.Builder().build()
val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .baseUrl(BASE_URL)
    .build()
val restApi = retrofit.create(RestAPI::class.java)

runBlocking {
    val inv = restApi.getInventory()
    println("Result: Sold ${inv.sold}, pending ${inv.pending}, available ${inv.available}")
}

exitProcess(0)

data class InventoryResponse (
    val sold: Int,
    val pending: Int,
    val available: Int
)

interface RestAPI {
    @GET("store/inventory")
	suspend fun getInventory(): InventoryResponse
}