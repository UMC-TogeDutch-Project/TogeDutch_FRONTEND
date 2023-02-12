package umc.mobile.project.announcement.Auth.PostPost

import android.os.Build
import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import umc.mobile.project.ram.my_application_1.user_id_var
import java.sql.Timestamp
import java.time.Instant
import java.util.*
var userPostId = 0

class PostRecordService {
    var timestamp = Timestamp(Date().time)
//var timestamp = Date(System.currentTimeMillis())
    private var result : Result = Result(post_id = 1, title = "", url =  "", delivery_tips = 1, minimum = 1, order_time = "timestamp",
        num_of_recruits =1 , recruited_num = 1, status =  "", created_at = timestamp,
    updated_at = timestamp, user_id = 1, image = "", latitude = 1.0, longitude = 1.0, chatRoom_id = 1, category = "")

    private lateinit var postRecordResult : PostRecordResult

    fun setRecordResult(postRecordResult: PostRecordResult){
        this.postRecordResult = postRecordResult
    }

    fun sendPost(user_id : Int, record: PostRecord, file : MultipartBody.Part?){
        Log.d("timestamp 값 ==========================", file.toString())
        if(file == null)
            Log.d("null 입니다 ==========================", user_id.toString())
        val authService = getRetrofit().create(PostRecordRetrofitInterfaces::class.java)
        authService.sendPost(user_id, record, file).enqueue(object: Callback<PostRecordResponse> {
            override fun onResponse(call: Call<PostRecordResponse>, response: Response<PostRecordResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                Log.d("postid", result.toString())
                val resp: PostRecordResponse = response.body()!!
                result = resp.result!!
                when(resp.code){
                    1000 ->{
                        postRecordResult.recordSuccess(result)
                        userPostId = result.post_id
                    }
                    else -> postRecordResult.recordFailure()
                }
            }

            override fun onFailure(call: Call<PostRecordResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}