package umc.mobile.project.wishlist.GetLikePost

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit

class LikePostGetService {
    private lateinit var likePostGetResult: LikePostGetResult

    fun setLikePostGetResult(likePostGetResult: LikePostGetResult){
        this.likePostGetResult = likePostGetResult
    }

    fun getLikePost(user_id : Int){
        val childGetService = getRetrofit().create(LikePostGetRetrofitInterface::class.java)

        childGetService.getLikePost(user_id).enqueue(object : Callback<LikePostGetResponse> {
            override fun onResponse(call: Call<LikePostGetResponse>, response: Response<LikePostGetResponse>,) {
                Log.d("LIKEPOST-GET SUCCESS",response.toString())
                val resp : LikePostGetResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                when(resp.code) {
                    1000 -> likePostGetResult.getPostUploadSuccess(resp.code, resp.result!!)
                    else -> likePostGetResult.getPostUploadFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<LikePostGetResponse>, t: Throwable) {
                Log.d("LIKEPOST-GET FAILURE",t.message.toString())
            }
        })
    }
}