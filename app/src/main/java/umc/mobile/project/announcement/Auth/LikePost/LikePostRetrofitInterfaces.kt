package umc.mobile.project.announcement.Auth.LikePost

import retrofit2.Call
import retrofit2.http.*

interface LikePostRetrofitInterfaces {

    @POST("user/{userIdx}/likePost/{postIdx}")
    fun sendPost( @Path("userIdx")userIdx: Int, @Path("postIdx")postIdx : Int) : Call<LikePostResponse>
}