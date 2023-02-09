package umc.mobile.project.restaurant.Auth.PlaceApi

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceGetService {
    private lateinit var postGetResult: PlaceGetResult

    fun setPostGetResult(postGetResult: PlaceGetResult){
        this.postGetResult = postGetResult
    }

    fun getPost(post_id : Int){
        val postUploadDetailGetService = getRetrofit().create(PlaceGetRetrofitInterfaces::class.java)

        postUploadDetailGetService.getPost(post_id).enqueue(object : Callback<PlaceGetResponse> {
            override fun onResponse(call: Call<PlaceGetResponse>, response: Response<PlaceGetResponse>,) {
                Log.d("POSTONE-GET SUCCESS",response.toString())
                val resp : PlaceGetResponse = response.body()!!
                when(resp.code) {
//                    1000 ->
                    1000 -> postGetResult.getPostSuccess(resp.code, resp.result!!)
                    else -> postGetResult.getPostFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<PlaceGetResponse>, t: Throwable) {
                Log.d("POSTONE-GET FAILURE",t.message.toString())
            }
        })
    }
}