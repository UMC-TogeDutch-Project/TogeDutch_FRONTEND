package umc.mobile.project.restaurant.Auth.PlaceApi


import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
//@Query("location") location: LatLng,
interface PlaceGetRetrofitInterfaces {
    @GET("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522%2C151.1957362")
    fun getPost ( @Query("radius") radius: Int, @Query("type") type: String, @Query("language") language: String, @Query("key") key: String): Call<PlaceGetResponse>
}