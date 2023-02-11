package umc.mobile.project.restaurant.Auth.PlaceApi


import android.location.Location
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceGetRetrofitInterfaces {
    @GET("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
    fun getPost (@Query("location") location: String, @Query("radius") radius: Int, @Query("type") type: String, @Query("keyword") keyword: String, @Query("language") language: String, @Query("key")key: String): Call<PlaceGetResponse>
}