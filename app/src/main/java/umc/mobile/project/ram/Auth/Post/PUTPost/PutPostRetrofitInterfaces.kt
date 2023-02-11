package umc.mobile.project.ram.Auth.Post.PUTPost

import retrofit2.Call
import retrofit2.http.*

interface PutPostRetrofitInterfaces {
    @PUT("/application/post/status/{postIdx}")
    fun putPost(@Path("postIdx") post_id : Int) : Call<PutPostResponse>
}