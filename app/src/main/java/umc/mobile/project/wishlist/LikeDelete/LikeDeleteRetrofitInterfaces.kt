package umc.mobile.project.wishlist.LikeDelete

import retrofit2.Call
import retrofit2.http.*

interface LikeDeleteRetrofitInterfaces {
    @DELETE("user/{userIdx}/likePost/{postIdx}")
    fun deleteWishlist( @Path("userIdx")user_id: Int, @Path("postIdx")post_id : Int) : Call<Int>
}