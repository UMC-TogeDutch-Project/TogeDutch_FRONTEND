package umc.mobile.project.restaurant.Auth.PlaceApi


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaceGetRetrofitInterfaces {
    @GET("post/postId/{postIdx}")
    fun getPost (@Path("postIdx") post_id: Int): Call<PlaceGetResponse>
}