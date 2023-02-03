package umc.mobile.project.ram.Auth.Post.GetPostJoin

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostJoinGetService {
    private lateinit var postJoinGetResult: PostJoinGetResult

    fun setPostJoinGetResult(postJoinGetResult: PostJoinGetResult){
        this.postJoinGetResult = postJoinGetResult
    }

    fun getPostJoin(user_id : Int){
        val postJoinGetService = getRetrofit().create(PostJoinGetRetrofitInterfaces::class.java)

        postJoinGetService.getPostJoin(user_id).enqueue(object : Callback<PostJoinGetResponse> {
            override fun onResponse(call: Call<PostJoinGetResponse>, response: Response<PostJoinGetResponse>,) {
                Log.d("POSTUPLOAD-GET SUCCESS",response.toString())
                val resp : PostJoinGetResponse = response.body()!!
                when(resp.code) {
//                    1000 ->
                    1000 -> postJoinGetResult.getPostJoinSuccess(resp.code, resp.result!!)
                    else -> postJoinGetResult.getPostJoinFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<PostJoinGetResponse>, t: Throwable) {
                Log.d("POSTUPLOAD-GET FAILURE",t.message.toString())
            }
        })
    }
}