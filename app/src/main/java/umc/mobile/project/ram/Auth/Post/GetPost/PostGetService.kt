package umc.mobile.project.ram.Auth.Post.GetPost

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostGetService {
    private lateinit var postGetResult: PostGetResult

    fun setPostGetResult(postGetResult: PostGetResult){
        this.postGetResult = postGetResult
    }

    fun getPost(post_id : Int){
        val postUploadDetailGetService = getRetrofit().create(PostGetRetrofitInterfaces::class.java)

        postUploadDetailGetService.getPost(post_id).enqueue(object : Callback<PostGetResponse> {
            override fun onResponse(call: Call<PostGetResponse>, response: Response<PostGetResponse>,) {
                Log.d("POSTONE-GET SUCCESS",response.toString())
                val resp : PostGetResponse = response.body()!!
                when(resp.code) {
//                    1000 ->
                    1000 -> postGetResult.getPostSuccess(resp.code, resp.result!!)
                    else -> postGetResult.getPostFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<PostGetResponse>, t: Throwable) {
                Log.d("POSTONE-GET FAILURE",t.message.toString())
            }
        })
    }
}