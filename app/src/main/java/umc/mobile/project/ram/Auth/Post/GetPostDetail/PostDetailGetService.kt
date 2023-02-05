package umc.mobile.project.ram.Auth.Post.GetPostDetail

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostDetailGetService {
    private lateinit var postDetailGetResult: PostDetailGetResult

    fun setPostDetailGetResult(postDetailGetResult: PostDetailGetResult){
        this.postDetailGetResult = postDetailGetResult
    }

    fun getPostDetail(post_id : Int, user_id : Int){
        val postUploadDetailGetService = getRetrofit().create(PostDetailGetRetrofitInterfaces::class.java)

        postUploadDetailGetService.getPostDetail(post_id, user_id).enqueue(object : Callback<PostDetailGetResponse> {
            override fun onResponse(call: Call<PostDetailGetResponse>, response: Response<PostDetailGetResponse>,) {
                Log.d("POSTUPLOAD-GET SUCCESS",response.toString())
                val resp : PostDetailGetResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                when(resp.code) {
//                    1000 ->
                    1000 -> postDetailGetResult.getPostUploadSuccess(resp.code, resp.result!!)
                    else -> postDetailGetResult.getPostUploadFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<PostDetailGetResponse>, t: Throwable) {
                Log.d("POSTUPLOAD-GET FAILURE",t.message.toString())
            }
        })
    }
}