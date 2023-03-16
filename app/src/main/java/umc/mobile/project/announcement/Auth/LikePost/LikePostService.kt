package umc.mobile.project.announcement.Auth.LikePost

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.DataImminentRVAdapter
import umc.mobile.project.getRetrofit

import java.sql.Timestamp
import java.util.*

class LikePostService {
    var timestamp = Timestamp(Date().time)
//var timestamp = Date(System.currentTimeMillis())
    //private var result : Result = Result(likeIdx = 0, postIdx = 0, post_User_userIdx = 0, like_userIdx = 0)

    private lateinit var likePostResult : LikePostResult

    fun setLikePostResult(likePostResult: LikePostResult){
        this.likePostResult = likePostResult
    }

    fun sendLike(userIdx : Int, postIdx : Int){
        val authService = getRetrofit().create(LikePostRetrofitInterfaces::class.java)
        authService.sendPost(userIdx,postIdx).enqueue(object: Callback<LikePostResponse> {
            override fun onResponse(call: Call<LikePostResponse>, response: Response<LikePostResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: LikePostResponse = response.body()!!
                when(resp.code){
                    1000 -> {likePostResult.LikePostSuccess(resp.result!!)
                            Log.d("postIdx: ", resp.result!!.postIdx.toString())
                            Log.d("userId", resp.result!!.like_userIdx.toString())}
                    2033 -> likePostResult.LikePostFailureMyPost()
                    2034 -> likePostResult.LikePostFailureAdd()
                }
            }

            override fun onFailure(call: Call<LikePostResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}