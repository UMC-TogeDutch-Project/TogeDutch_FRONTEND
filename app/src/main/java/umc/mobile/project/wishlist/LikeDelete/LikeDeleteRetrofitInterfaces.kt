package umc.mobile.project.wishlist.LikeDelete

import retrofit2.Call
import retrofit2.http.*

interface LikeDeleteRetrofitInterfaces {

    @DELETE("user/{userIdx}/likePost/{postIdx}")
    fun sendPost( @Path("userIdx")userIdx: Int, @Path("postIdx")postIdx : Int) : Call<Int>
}