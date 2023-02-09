package umc.mobile.project.ram.Auth.Post.PUTRetouch

import Post
import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class PutRetouchService {

    var timestamp = Timestamp(Date().time)
    //var timestamp = Date(System.currentTimeMillis())
    private var result : Result = Result(post_id = 1, title = "", url =  "", delivery_tips = 1, minimum = 1, order_time = "timestamp",
        num_of_recruits =1 , recruited_num = 1, status =  "", created_at = timestamp,
        updated_at = timestamp, user_id = 1, image = "", latitude = 1.0, longitude = 1.0, chatRoom_id = 1, category = "")

    private lateinit var putRetouchResult : PutRetouchResult

    fun setPutRetouchResult(putRetouchResult: PutRetouchResult){
        this.putRetouchResult = putRetouchResult
    }

    fun putRetouch(post_id : Int, user_id : Int, record: Request_put, file : MultipartBody.Part?){
        val authService = getRetrofit().create(PutRetouchRetrofitInterfaces::class.java)
        authService.putRetouch(post_id,  record, user_id, file).enqueue(object: Callback<PutRetouchResponse> {
            override fun onResponse(call: Call<PutRetouchResponse>, response: Response<PutRetouchResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: PutRetouchResponse = response.body()!!
                result = resp.result!!
                when(resp.code){
                    1000 -> putRetouchResult.PutRetouchSuccess(result)
                    else -> putRetouchResult.PutRetouchFailure()
                }
            }

            override fun onFailure(call: Call<PutRetouchResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}