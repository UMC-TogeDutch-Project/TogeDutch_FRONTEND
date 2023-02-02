package umc.mobile.project.ram.Auth.Application.ViewUpload

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewUploadGetService {
    private lateinit var viewUploadGetResult: ViewUploadGetResult

    fun setViewUploadGetResult(postDetailGetResult: ViewUploadGetResult){
        this.viewUploadGetResult = postDetailGetResult
    }

    fun getViewUpload(user_id : Int){
        val postUploadDetailGetService = getRetrofit().create(ViewUploadGetRetrofitInterfaces::class.java)

        postUploadDetailGetService.getViewUpload(user_id).enqueue(object : Callback<ViewUploadGetResponse> {
            override fun onResponse(call: Call<ViewUploadGetResponse>, response: Response<ViewUploadGetResponse>,) {
                Log.d("POSTUPLOAD-GET SUCCESS",response.toString())
                val resp : ViewUploadGetResponse = response.body()!!
                when(resp.code) {
//                    1000 ->
                    1000 -> viewUploadGetResult.getPostUploadSuccess(resp.code, resp.result!!)
                    else -> viewUploadGetResult.getPostUploadFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<ViewUploadGetResponse>, t: Throwable) {
                Log.d("POSTUPLOAD-GET FAILURE",t.message.toString())
            }
        })
    }
}