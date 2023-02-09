package umc.mobile.project.wishlist.GetLikePost

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface LikePostGetRetrofitInterface {
    @GET("user/{userIdx}/likePost")
    fun getLikePost (@Path("userIdx") user_id: Int): Call<LikePostGetResponse>
}