package umc.mobile.project.wishlist.LikeDelete

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit

class LikeDeleteService {
    private lateinit var likeDeleteResult : LikeDeleteResult

    fun setDeletePostResult(likeDeleteResult: LikeDeleteResult){
        this.likeDeleteResult = likeDeleteResult
    }

    fun deleteLike(user_id : Int, post_id : Int){
        val authService = getRetrofit().create(LikeDeleteRetrofitInterfaces::class.java)
        authService.deleteWishlist(user_id, post_id).enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Log.d("DELETE/SUCCESS", response.toString())
                val resp: Int = response.body()!!
                Log.d("success code: ", resp.toString())
                when(resp){
                    1 -> likeDeleteResult.getLikeDeleteSuccess(resp!!)
                    else -> likeDeleteResult.getLikeDeleteFailure(resp!!)
                }
            }


            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.d("DELETE/FAILURE",t.message.toString())
            }
        })
    }
}