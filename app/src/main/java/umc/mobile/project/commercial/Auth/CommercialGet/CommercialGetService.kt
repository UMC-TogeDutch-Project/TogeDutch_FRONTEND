package umc.mobile.project.commercial.Auth.CommercialGet

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.commercial.kakao_url
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

class CommercialGetService {
    var timestamp = Timestamp(Date().time)

    private lateinit var commercialGetResult : CommercialGetResult

    fun setCommercialGetResult(commercialGetResult: CommercialGetResult){
        this.commercialGetResult = commercialGetResult
    }

    fun sendPost(user_id : Int){

        val authService = getRetrofit().create(CommercialGetRetrofitInterfaces::class.java)
        authService.sendPost(user_id).enqueue(object: Callback<CommercialGetResponse> {
            override fun onResponse(call: Call<CommercialGetResponse>, response: Response<CommercialGetResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: CommercialGetResponse = response.body()!!
                when(resp.code){
                    1000 -> commercialGetResult.commercialGetSuccess(resp.result)
                    else -> commercialGetResult.commercialGetFailure()
                }
            }

            override fun onFailure(call: Call<CommercialGetResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}