package umc.mobile.project.ram.Auth.Post.GetPostDetail


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostDetailGetRetrofitInterfaces {
    @GET("post/{post_id}/")
    fun getPostDetail (@Path("post_id") post_id: Int, @Query("user") user_id: Int): Call<PostDetailGetResponse>
}