package uz.pdp.card.networking

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.pdp.card.model.Card

@JvmSuppressWildcards
interface Service {

    @GET("cards")
    fun getCards():Call<List<Card>>

    @GET("cards/{id}")
    fun getCard(@Path("id") id:Int):Call<Card>

    @POST("cards")
    fun addCard(@Body card: Card):Call<Card>


}