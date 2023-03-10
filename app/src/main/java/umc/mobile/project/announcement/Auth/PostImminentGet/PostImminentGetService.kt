package umc.mobile.project.announcement.Auth.PostImminentGet

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit

class PostImminentGetService {

    private lateinit var postImminentGetResult : PostImminentGetResult

    fun setPostGetResult(postImminentGetResult: PostImminentGetResult){
        this.postImminentGetResult = postImminentGetResult
    }

    fun getPost(){
        val authService = getRetrofit().create(PostImminentGetRetrofitInterfaces::class.java)
        authService.getPost().enqueue(object: Callback<PostImminentResponse> {
            override fun onResponse(call: Call<PostImminentResponse>, response: Response<PostImminentResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: PostImminentResponse = response.body()!!
                when(resp.code){
                    1000 -> postImminentGetResult.recordSuccess1(resp.result)
                    else -> postImminentGetResult.recordFailure1()
                }
            }

            override fun onFailure(call: Call<PostImminentResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}