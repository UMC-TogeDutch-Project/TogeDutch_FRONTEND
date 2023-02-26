package umc.mobile.project.ram.Auth.ChatLocation

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.announcement.Auth.PostPost.userPostId
import umc.mobile.project.getRetrofit
import umc.mobile.project.latitude_var
import umc.mobile.project.ram.Auth.ChatPhoto.ChatPhotoPost.PostPhotoResponse
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*

class PostLocationService {

    var timestamp = Timestamp(Date().time)
    private var result : Result = Result(chatLocationIdx = 1, chatRoomId = 1, userId = 1, latitude = BigDecimal.ONE, longitude= BigDecimal.ONE, createdAt = timestamp)

    private lateinit var postLocationResult: PostLocationResult

    fun setLocationResult(postLocationResult: PostLocationResult){
        this.postLocationResult = postLocationResult
    }

    fun sendLocation(chatRoom_id : Int, user : Int, latitude : BigDecimal, longitude : BigDecimal){
        val authService = getRetrofit().create(PostLocationRetrofitInterfaces::class.java)
        authService.sendLocation(chatRoom_id, user, latitude, longitude).enqueue(object: Callback<PostLocationResponse> {
            override fun onResponse(call: Call<PostLocationResponse>, response: Response<PostLocationResponse>) {
                Log.d("SNED-LOCATION SUCCESS",response.toString())
                if(response.body() != null) {
                    val resp: PostLocationResponse = response.body()!!
                    result = resp.result!!
                    when (resp.code) {
                        1000 -> postLocationResult.sendLocationSuccess(result)
                        else -> postLocationResult.sendLocationFailure()
                    }
                }
                else{
                    Log.d("SNED-LOCATION FAILURE","null")
                }
            }

            override fun onFailure(call: Call<PostLocationResponse>, t: Throwable) {
                Log.d("SNED-LOCATION FAILURE",t.message.toString())
            }
        })
    }
}