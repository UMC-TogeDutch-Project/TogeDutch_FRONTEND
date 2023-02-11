package umc.mobile.project.ram.Auth.Post.DeletePost

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit

class DeletePostService {
    private lateinit var deletePostResult: DeletePostResult

    fun setDeletePostResult(deletePostResult: DeletePostResult){
        this.deletePostResult = deletePostResult
    }

    fun deletePost( post_id : Int){
        val authService = getRetrofit().create(DeletePostRetrofitInterfaces::class.java)
        authService.deletePost(post_id).enqueue(object: Callback<DeletePostResponse> {
            override fun onResponse(call: Call<DeletePostResponse>, response: Response<DeletePostResponse>) {
                Log.d("POSTUPLOAD-GET SUCCESS",response.toString())
                val resp : DeletePostResponse = response.body()!!
                when(resp.code) {
//                    1000 ->
                    1000 -> deletePostResult.deletePostSuccess(resp.result!!)
                    else -> deletePostResult.deletePostFailure()
                }
            }

            override fun onFailure(call: Call<DeletePostResponse>, t: Throwable) {
                Log.d("SNED-ACCEPT FAILURE",t.message.toString())
            }
        })
    }
}