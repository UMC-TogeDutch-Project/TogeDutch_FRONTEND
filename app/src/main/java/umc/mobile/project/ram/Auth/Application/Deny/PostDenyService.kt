package umc.mobile.project.ram.Auth.Application.Deny

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class PostDenyService {

    private var result : Result = Result(application_id = 1, status = "", post_id = 1, user_id = 1, chatRoom_id = 1)

    private lateinit var postDenyResult: PostDenyResult

    fun setDenyResult(postDenyResult: PostDenyResult){
        this.postDenyResult = postDenyResult
    }

    fun sendDeny(applicationIdx : Int){
        val authService = getRetrofit().create(PostDenyRetrofitInterfaces::class.java)
        authService.sendDeny(applicationIdx).enqueue(object: Callback<PostDenyResponse> {
            override fun onResponse(call: Call<PostDenyResponse>, response: Response<PostDenyResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: PostDenyResponse = response.body()!!
                result = resp.result!!
                when(resp.code){
                    1000 -> postDenyResult.DenySuccess(result)
                    else -> postDenyResult.DenyFailure()
                }
            }

            override fun onFailure(call: Call<PostDenyResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}