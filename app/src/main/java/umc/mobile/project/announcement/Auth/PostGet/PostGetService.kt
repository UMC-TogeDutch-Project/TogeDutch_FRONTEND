package umc.mobile.project.announcement.Auth.PostGet

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.announcement.Auth.PostGet.PostGetResult
import umc.mobile.project.getRetrofit

class PostGetService {

    private lateinit var postGetResult : PostGetResult

    fun setPostGetResult(postGetResult: PostGetResult){
        this.postGetResult = postGetResult
    }

    fun getPost(type : String){
        val authService = getRetrofit().create(PostGetRetrofitInterfaces::class.java)
        authService.getPost(type).enqueue(object: Callback<PostGetResponse> {
            override fun onResponse(call: Call<PostGetResponse>, response: Response<PostGetResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: PostGetResponse = response.body()!!
                when(resp.code){
                    1000 -> postGetResult.recordSuccess(resp.result)
                    else -> postGetResult.recordFailure()
                }
            }

            override fun onFailure(call: Call<PostGetResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}