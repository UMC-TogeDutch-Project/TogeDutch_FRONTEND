package umc.mobile.project.ram.Auth.Post.GetPostUpload


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostUploadGetRetrofitInterfaces {
    @GET("post/all/{userIdx}")
    fun getPostUpload (@Path("userIdx") user_id: Int): Call<PostUploadGetResponse>
}