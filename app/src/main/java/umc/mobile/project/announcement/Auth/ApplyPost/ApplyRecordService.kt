package umc.mobile.project.announcement.Auth.ApplyPost

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class ApplyRecordService {
    var timestamp = Timestamp(Date().time)
//var timestamp = Date(System.currentTimeMillis())
    private var result : Result = Result(application_id = 1, status = "", post_id = 1, user_id = 1, chatRoom_id = 1)

    private lateinit var applyRecordResult : ApplyRecordResult

    fun setApplyRecordResult(applyRecordResult: ApplyRecordResult){
        this.applyRecordResult = applyRecordResult
    }

    fun sendApply(postIdx : Int){
        val authService = getRetrofit().create(ApplyRecordRetrofitInterfaces::class.java)
        authService.sendPost(postIdx).enqueue(object: Callback<ApplyRecordResponse> {
            override fun onResponse(call: Call<ApplyRecordResponse>, response: Response<ApplyRecordResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: ApplyRecordResponse = response.body()!!
                result = resp.result!!
                when(resp.code){
                    1000 -> applyRecordResult.applyRecordSuccess(result)
                    else -> applyRecordResult.applyRecordFailure()
                }
            }

            override fun onFailure(call: Call<ApplyRecordResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}