package umc.mobile.project.ram.Auth.Post.GetPostUpload

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostUploadGetService {
    private lateinit var postUploadGetResult: PostUploadGetResult

    fun setPostUploadGetResult(postUploadGetResult: PostUploadGetResult){
        this.postUploadGetResult = postUploadGetResult
    }

    fun getPostUpload(user_id : Int){
        val childGetService = getRetrofit().create(PostUploadGetRetrofitInterfaces::class.java)

        childGetService.getPostUpload(user_id).enqueue(object : Callback<PostUploadGetResponse> {
            override fun onResponse(call: Call<PostUploadGetResponse>, response: Response<PostUploadGetResponse>,) {
                Log.d("POSTUPLOAD-GET SUCCESS",response.toString())
                val resp : PostUploadGetResponse = response.body()!!
                when(resp.code) {
//                    1000 ->
                    1000 -> postUploadGetResult.getPostUploadSuccess(resp.code, resp.result!!)
                    else -> postUploadGetResult.getPostUploadFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<PostUploadGetResponse>, t: Throwable) {
                Log.d("POSTUPLOAD-GET FAILURE",t.message.toString())
            }
        })
    }
}