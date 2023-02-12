package umc.mobile.project.ram.Auth.Post.PUTPostStatus

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class PutPostStatusService {

    var timestamp = Timestamp(Date().time)
    //var timestamp = Date(System.currentTimeMillis())
    private var result : Result = Result(post_id = 1, title = "", url =  "", delivery_tips = 1, minimum = 1, order_time = "timestamp",
        num_of_recruits =1 , recruited_num = 1, status =  "", created_at = timestamp,
        updated_at = timestamp, user_id = 1, image = "", latitude = 1.0, longitude = 1.0, chatRoom_id = 1, category = "")

    private lateinit var putPostStatusResult : PutPostStatusResult

    fun setPutPostStatusResult(putPostStatusResult: PutPostStatusResult){
        this.putPostStatusResult = putPostStatusResult
    }

    fun putPostStatus(post_id : Int){
        val authService = getRetrofit().create(PutPostStatusRetrofitInterfaces::class.java)
        authService.putPostStatus(post_id).enqueue(object: Callback<PutPostStatusResponse> {
            override fun onResponse(call: Call<PutPostStatusResponse>, response: Response<PutPostStatusResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: PutPostStatusResponse = response.body()!!
                result = resp.result!!
                when(resp.code){
                    1000 -> putPostStatusResult.PutPostStatusSuccess(result)
                    else -> putPostStatusResult.PutPostStatusFailure()
                }
            }

            override fun onFailure(call: Call<PutPostStatusResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}