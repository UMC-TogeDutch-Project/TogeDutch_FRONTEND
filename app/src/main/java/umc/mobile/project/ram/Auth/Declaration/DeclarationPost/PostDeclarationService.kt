package umc.mobile.project.ram.Auth.Declaration.DeclarationPost

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class PostDeclarationService {
    var timestamp = Timestamp(Date().time)
    private var result : Result = Result(declarationIdx = 1, content ="", created_at = timestamp, status = "", chatRoomIdx = 1)

    private lateinit var postDeclarationResult: PostDeclarationResult

    fun setDeclarationResult(postDeclarationResult: PostDeclarationResult){
        this.postDeclarationResult = postDeclarationResult
    }

    fun sendDeclaration(chatRoom_id : Int, content : declarationPost){
        val authService = getRetrofit().create(PostDeclarationRetrofitInterfaces::class.java)
        authService.sendDeclaration(chatRoom_id, content).enqueue(object: Callback<PostDeclarationResponse> {
            override fun onResponse(call: Call<PostDeclarationResponse>, response: Response<PostDeclarationResponse>) {
                Log.d("SNED-ACCEPT SUCCESS",response.toString())
                if(response.body() != null) {
                    val resp: PostDeclarationResponse = response.body()!!
                    result = resp.result!!
                    when (resp.code) {
                        1000 -> postDeclarationResult.sendDeclarationSuccess(result)
                        else -> postDeclarationResult.sendDeclarationFailure()
                    }
                }
                else{
                    Log.d("SNED-ACCEPT FAILURE","null")
                }
            }

            override fun onFailure(call: Call<PostDeclarationResponse>, t: Throwable) {
                Log.d("SNED-ACCEPT FAILURE",t.message.toString())
            }
        })
    }
}