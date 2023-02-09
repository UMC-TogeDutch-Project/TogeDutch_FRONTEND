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
    private var result : Post = Post(post_id = 0, title = "", url = "", delivery_tips = 0, minimum = 0, order_time = "", num_of_recruits = 0, recruited_num = 0,
        status = "", created_at = timestamp, updated_at = timestamp, image = "", latitude = 0.0, longitude = 0.0, chatRoom_id = 0, category = "", user_id = 0)

    private lateinit var putRetouchResult: PutRetouchResult

    fun setPutRetouchResult(putRetouchResult: PutRetouchResult){
        this.putRetouchResult = putRetouchResult
    }

    fun putRetouch(post_id : Int, user_id : Int, request : Request_put, file : MultipartBody.Part?){
        val authService = getRetrofit().create(PutRetouchRetrofitInterfaces::class.java)
        authService.putRetouch(post_id, user_id, request, file).enqueue(object: Callback<PutRetouchResponse> {
            override fun onResponse(call: Call<PutRetouchResponse>, response: Response<PutRetouchResponse>) {
                Log.d("CHAT-POST SUCCESS",response.toString())
                val resp: PutRetouchResponse = response.body()!!
                result = resp.result!!
                when(resp.code){
                    1000 -> putRetouchResult.PutRetouchSuccess(result)
                    else -> putRetouchResult.PutRetouchFailure()
                }
            }

            override fun onFailure(call: Call<PutRetouchResponse>, t: Throwable) {
                Log.d("CHAT-POST FAILURE",t.message.toString())
            }
        })
    }
}