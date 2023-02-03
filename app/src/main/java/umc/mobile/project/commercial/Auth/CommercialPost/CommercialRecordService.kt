package umc.mobile.project.commercial.Auth.CommercialPost

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.util.*

class CommercialRecordService {
//var timestamp = Date(System.currentTimeMillis())
    private var result : Result = Result(tid = "", next_redirect_pc_url = "", next_redirect_mobile_url = "", partner_order_id = 1, created_at = 1)

    private lateinit var commercialRecordResult : CommercialRecordResult

    fun setRecordResult(commercialRecordResult: CommercialRecordResult){
        this.commercialRecordResult = commercialRecordResult
    }

    fun sendPost(user_id : Int, record: CommercialRecord, file : MultipartBody.Part?){

        val authService = getRetrofit().create(CommercialRecordRetrofitInterfaces::class.java)
        authService.sendPost(user_id, record, file).enqueue(object: Callback<PostRecordResponse> {
            override fun onResponse(call: Call<PostRecordResponse>, response: Response<PostRecordResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: PostRecordResponse = response.body()!!
                result = resp.result!!
                when(resp.code){
                    1000 -> commercialRecordResult.recordSuccess(result)
                    else -> commercialRecordResult.recordFailure()
                }
            }

            override fun onFailure(call: Call<PostRecordResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}