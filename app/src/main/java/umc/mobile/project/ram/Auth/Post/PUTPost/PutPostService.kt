package umc.mobile.project.ram.Auth.Post.PUTPost

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class PutPostService {

    var timestamp = Timestamp(Date().time)
    //var timestamp = Date(System.currentTimeMillis())
    private var result : Result = Result(post_id = 1, title = "", url =  "", delivery_tips = 1, minimum = 1, order_time = "timestamp",
        num_of_recruits =1 , recruited_num = 1, status =  "", created_at = timestamp,
        updated_at = timestamp, user_id = 1, image = "", latitude = 1.0, longitude = 1.0, chatRoom_id = 1, category = "")

    private lateinit var putPostResult : PutPostResult

    fun setPutPostResult(putPostResult: PutPostResult){
        this.putPostResult = putPostResult
    }

    fun putPost(post_id : Int){
        val authService = getRetrofit().create(PutPostRetrofitInterfaces::class.java)
        authService.putPost(post_id).enqueue(object: Callback<PutPostResponse> {
            override fun onResponse(call: Call<PutPostResponse>, response: Response<PutPostResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: PutPostResponse = response.body()!!
                result = resp.result!!
                when(resp.code){
                    1000 -> putPostResult.PutPostSuccess(result)
                    else -> putPostResult.PutPostFailure()
                }
            }

            override fun onFailure(call: Call<PutPostResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}