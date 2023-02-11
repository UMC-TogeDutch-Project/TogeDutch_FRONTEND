package umc.mobile.project.ram.Auth.ChatPhoto.ChatPhotoPost

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class PostPhotoService {

    var timestamp = Timestamp(Date().time)
    private var result : Result = Result(chatId = 1,   createdAt = timestamp, chatRoomId = 1, user_id = 1, image = "" )

    private lateinit var postPhotoResult: PostPhotoResult

    fun setPhotoResult(postPhotoResult: PostPhotoResult){
        this.postPhotoResult = postPhotoResult
    }

    fun sendPhoto(chatRoom_id : Int, user : Int, photo : MultipartBody.Part){
        val authService = getRetrofit().create(PostPhotoRetrofitInterfaces::class.java)
        authService.sendPhoto(chatRoom_id, user, photo).enqueue(object: Callback<PostPhotoResponse> {
            override fun onResponse(call: Call<PostPhotoResponse>, response: Response<PostPhotoResponse>) {
                Log.d("SNED-ACCEPT SUCCESS",response.toString())
                if(response.body() != null) {
                    val resp: PostPhotoResponse = response.body()!!
                    result = resp.result!!
                    when (resp.code) {
                        1000 -> postPhotoResult.sendPhotoSuccess(result)
                        else -> postPhotoResult.sendPhotoFailure()
                    }
                }
                else{
                    Log.d("SNED-ACCEPT FAILURE","null")
                }
            }

            override fun onFailure(call: Call<PostPhotoResponse>, t: Throwable) {
                Log.d("SNED-ACCEPT FAILURE",t.message.toString())
            }
        })
    }
}