package umc.mobile.project.commercial.Auth.CommercialPost

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.commercial.commercial_tid
import umc.mobile.project.commercial.kakao_url
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class CommercialRecordService {
    var timestamp = Timestamp(Date().time)
    private var result : Result = Result(tid = "", next_redirect_pc_url = "", next_redirect_mobile_url = "", partner_order_id = 1, created_at = timestamp)

    private lateinit var commercialRecordResult : CommercialRecordResult

    fun setCommercialRecordResult(commercialRecordResult: CommercialRecordResult){
        this.commercialRecordResult = commercialRecordResult
    }

    fun sendPost(user_id : Int, record: CommercialRecord, file : MultipartBody.Part?){

        val authService = getRetrofit().create(CommercialRecordRetrofitInterfaces::class.java)
        authService.sendPost(user_id, record, file).enqueue(object: Callback<CommercialRecordResponse> {
            override fun onResponse(call: Call<CommercialRecordResponse>, response: Response<CommercialRecordResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: CommercialRecordResponse = response.body()!!
                result = resp.result!!
                when(resp.code){
                    1000 -> {commercialRecordResult.commercialRecordSuccess(result)
                    kakao_url = resp.result.next_redirect_mobile_url }
                    else -> commercialRecordResult.commercialRecordFailure()
                }
            }

            override fun onFailure(call: Call<CommercialRecordResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}