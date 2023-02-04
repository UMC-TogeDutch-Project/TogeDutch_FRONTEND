package umc.mobile.project.ram.Auth.Post.GetPost


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostGetRetrofitInterfaces {
    @GET("post/postId/{postIdx}")
    fun getPost (@Path("postIdx") post_id: Int): Call<PostGetResponse>
}