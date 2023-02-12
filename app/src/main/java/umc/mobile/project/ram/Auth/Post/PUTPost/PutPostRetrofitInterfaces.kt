package umc.mobile.project.ram.Auth.Post.PUTPost

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface PutPostRetrofitInterfaces {
    @Multipart
    @PUT("/application/post/status/{postIdx}")
    fun putPost(@Path("postIdx") post_id : Int) : Call<PutPostResponse>
}