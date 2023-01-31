package umc.mobile.project.announcement.Auth.PostRecentGet

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit

class PostRecentGetService {

    private lateinit var postRecentGetResult : PostRecentGetResult

    fun setPostGetResult(postRecentGetResult: PostRecentGetResult){
        this.postRecentGetResult = postRecentGetResult
    }

    fun getPost(type : String){
        val authService = getRetrofit().create(PostRecentGetRetrofitInterfaces::class.java)
        authService.getPost(type).enqueue(object: Callback<PostRecentGetResponse> {
            override fun onResponse(call: Call<PostRecentGetResponse>, response: Response<PostRecentGetResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: PostRecentGetResponse = response.body()!!
                when(resp.code){
                    1000 -> postRecentGetResult.recordSuccess(resp.result)
                    else -> postRecentGetResult.recordFailure()
                }
            }

            override fun onFailure(call: Call<PostRecentGetResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}