package umc.mobile.project.ram.Auth.Post.GetPostAll


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostGetAllRetrofitInterfaces {
    @GET("post")
    fun getPostAll (): Call<PostGetAllResponse>
}