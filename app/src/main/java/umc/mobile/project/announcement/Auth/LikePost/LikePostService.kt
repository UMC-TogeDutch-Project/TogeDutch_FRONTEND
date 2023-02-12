package umc.mobile.project.announcement.Auth.LikePost

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.DataImminentRVAdapter
import umc.mobile.project.getRetrofit
import umc.mobile.project.likeId
import umc.mobile.project.postUserIdx
import java.sql.Timestamp
import java.util.*

class LikePostService {
    var timestamp = Timestamp(Date().time)
//var timestamp = Date(System.currentTimeMillis())
    private var result : Result = Result(likeIdx = 1, postIdx = 1, Post_User_userIdx = 1, Like_userIdx = 1)

    private lateinit var likePostResult : LikePostResult

    fun setLikePostResult(likePostResult: LikePostResult){
        this.likePostResult = likePostResult
    }

    fun sendLike(userIdx : Int, postIdx : Int){
        val authService = getRetrofit().create(LikePostRetrofitInterfaces::class.java)
        authService.sendPost(userIdx,postIdx).enqueue(object: Callback<ApplyRecordResponse> {
            override fun onResponse(call: Call<ApplyRecordResponse>, response: Response<ApplyRecordResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: ApplyRecordResponse = response.body()!!

                when(resp.code){
                    1000 -> likePostResult.LikePostSuccess(result)
                    2033 -> likePostResult.LikePostFailureMyPost()
                    2034 -> likePostResult.LikePostFailureAdd()
                }
            }

            override fun onFailure(call: Call<ApplyRecordResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}