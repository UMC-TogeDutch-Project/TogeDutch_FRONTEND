package umc.mobile.project.commercial.Auth.CommercialDetailGet

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommercialDetailGetService {
    private lateinit var commercialDetailGetResult: CommercialDetailGetResult

    fun setCommercialDetailGetResult(commercialDetailGetResult: CommercialDetailGetResult){
        this.commercialDetailGetResult = commercialDetailGetResult
    }

    fun getCommercialDetail(post_id : Int){
        val commercialUploadDetailGetService = getRetrofit().create(CommercialDetailGetRetrofitInterfaces::class.java)

        commercialUploadDetailGetService.getPostDetail(post_id).enqueue(object : Callback<CommercialDetailGetResponse> {
            override fun onResponse(call: Call<CommercialDetailGetResponse>, response: Response<CommercialDetailGetResponse>,) {
                Log.d("COMMERCIALUPLOAD-GET SUCCESS",response.toString())
                val resp : CommercialDetailGetResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                when(resp.code) {
//                    1000 ->
                    1000 -> commercialDetailGetResult.getCommercialUploadSuccess(resp.code, resp.result!!)
                    else -> commercialDetailGetResult.getCommercialUploadFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<CommercialDetailGetResponse>, t: Throwable) {
                Log.d("COMMERCIALUPLOAD-GET FAILURE",t.message.toString())
            }
        })
    }
}