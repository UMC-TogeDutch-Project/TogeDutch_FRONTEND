package umc.mobile.project.ram.Auth.Post.GetPostChatIdx

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostChatIdxGetService {
    private lateinit var postChatIdxGetResult: PostChatIdxGetResult

    fun setPostChatIdxGetResult(postChatIdxGetResult: PostChatIdxGetResult){
        this.postChatIdxGetResult = postChatIdxGetResult
    }

    fun getPostChatIdx(user_id : Int){
        val postChatIdxGetService = getRetrofit().create(PostChatIdxGetRetrofitInterfaces::class.java)

        postChatIdxGetService.getPostChatIdx(user_id).enqueue(object : Callback<PostChatIdxGetResponse> {
            override fun onResponse(call: Call<PostChatIdxGetResponse>, response: Response<PostChatIdxGetResponse>,) {
                Log.d("POSTUPLOAD-GET SUCCESS",response.toString())
                if(response.body() != null) {
                    val resp: PostChatIdxGetResponse = response.body()!!
                    when (resp.code) {
                        1000 -> postChatIdxGetResult.getPostChatIdxSuccess(resp.code, resp.result!!)
                        else -> postChatIdxGetResult.getPostChatIdxFailure(resp.code, resp.message)
                    }
                }
                else
                    Log.d("POSTUPLOAD-GET FAILURE","")
            }

            override fun onFailure(call: Call<PostChatIdxGetResponse>, t: Throwable) {
                Log.d("POSTUPLOAD-GET FAILURE",t.message.toString())
            }
        })
    }
}