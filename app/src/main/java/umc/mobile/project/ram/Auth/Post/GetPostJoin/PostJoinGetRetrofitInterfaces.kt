package umc.mobile.project.ram.Auth.Post.GetPostJoin


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostJoinGetRetrofitInterfaces {
    @GET("post/join/{userIdx}")
    fun getPostJoin (@Path("userIdx") user_id: Int): Call<PostJoinGetResponse>
}