package umc.mobile.project.ram.Auth.Application.Accept

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.util.*

class PostAcceptService {

    private var result : Result = Result(application_id = 1, status = "", post_id = 1, user_id = 1, chatRoom_id = 1)

    private lateinit var postAcceptResult: PostAcceptResult

    fun setAcceptResult(postAcceptResult: PostAcceptResult){
        this.postAcceptResult = postAcceptResult
    }

    fun sendAccept(applicationIdx : Int){
        val authService = getRetrofit().create(PostAcceptRetrofitInterfaces::class.java)
        authService.sendAccept(applicationIdx).enqueue(object: Callback<PostAcceptResponse> {
            override fun onResponse(call: Call<PostAcceptResponse>, response: Response<PostAcceptResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: PostAcceptResponse = response.body()!!
                result = resp.result!!
                when(resp.code){
                    1000 -> postAcceptResult.AcceptSuccess(result)
                    else -> postAcceptResult.AcceptFailure()
                }
            }

            override fun onFailure(call: Call<PostAcceptResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}