package umc.mobile.project.ram.Auth.Post.GetPostAll

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostGetAllService {
    private lateinit var postGetAllResult: PostGetAllResult

    fun setPostGetResult(postGetAllResult: PostGetAllResult){
        this.postGetAllResult = postGetAllResult
    }

    fun getPostAll(){
        val postUploadDetailGetService = getRetrofit().create(PostGetAllRetrofitInterfaces::class.java)

        postUploadDetailGetService.getPostAll().enqueue(object : Callback<PostGetAllResponse> {
            override fun onResponse(call: Call<PostGetAllResponse>, response: Response<PostGetAllResponse>,) {
                Log.d("POSTUPLOAD-GET SUCCESS",response.toString())
                val resp : PostGetAllResponse = response.body()!!
                when(resp.code) {
//                    1000 ->
                    1000 -> postGetAllResult.getPostAllSuccess(resp.code, resp.result!!)
                    else -> postGetAllResult.getPostAllFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<PostGetAllResponse>, t: Throwable) {
                Log.d("POSTUPLOAD-GET FAILURE",t.message.toString())
            }
        })
    }
}