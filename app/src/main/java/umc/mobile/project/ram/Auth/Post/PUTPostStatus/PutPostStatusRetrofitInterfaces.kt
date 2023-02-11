package umc.mobile.project.ram.Auth.Post.PUTPostStatus

import retrofit2.Call
import retrofit2.http.*

interface PutPostStatusRetrofitInterfaces {
    @PUT("/post/status/{postIdx}")
    fun putPostStatus(@Path("postIdx") post_id : Int) : Call<PutPostStatusResponse>
}