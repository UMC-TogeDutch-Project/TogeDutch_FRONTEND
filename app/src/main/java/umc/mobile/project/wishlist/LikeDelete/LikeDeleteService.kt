package umc.mobile.project.wishlist.LikeDelete

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*
var rp = 1

class LikeDeleteService {
    var timestamp = Timestamp(Date().time)
//var timestamp = Date(System.currentTimeMillis())
//    private var result : Result = Result(likeIdx = 1, postIdx = 1, Post_User_userIdx = 1, Like_userIdx = 1)

    private lateinit var likeDeleteResult : LikeDeleteResult

    fun setLikePostResult(likeDeleteResult: LikeDeleteResult){
        this.likeDeleteResult = likeDeleteResult
    }

    fun sendLike(userIdx : Int, postIdx : Int){
        val authService = getRetrofit().create(LikeDeleteRetrofitInterfaces::class.java)
        authService.sendPost(userIdx,postIdx).enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Log.d("DELETE/SUCCESS", response.toString())
//                val resp: ApplyRecordResponse = response.body()!!

                when(response.body().toString().toInt()){
                    1 -> {
                        likeDeleteResult.LikeDeleteSuccess()
                        rp = response.body().toString().toInt()
                    }
                    else -> {
                        likeDeleteResult.LikeDeleteFailure()
                        rp = response.body().toString().toInt()
                    }
                }
            }


            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.d("DELETE/FAILURE",t.toString())
            }
        })
    }
}