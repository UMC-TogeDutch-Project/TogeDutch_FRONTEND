package umc.mobile.project.mypage.ChangePassword

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class PasswordPatchService {
    var timestamp = Timestamp(Date().time)
    //var timestamp = Date(System.currentTimeMillis())
    private var result : ChangePasswordResult = ChangePasswordResult(userIdx = 1, keywordIdx = 1, name =  "", role = "", email = "", password = "",
        phone="", image = "" , status =  "", created_at = timestamp,
        updated_at = timestamp, latitude = 1.0, longitude = 1.0, jwt = "")

    private lateinit var passwordPatchResult: PasswordPatchResult

    fun setPasswordPatchResult(passwordPatchResult: PasswordPatchResult){
        this.passwordPatchResult = passwordPatchResult
    }

    fun changePassword(jwt : String, userIdx : Int, password : PasswordPatchRequest){
        val authService = getRetrofit().create(PasswordPatchRetrofitInterface::class.java)
        authService.changePassword(jwt, userIdx, password).enqueue(object: Callback<PasswordPatchResponse> {
            override fun onResponse(call: Call<PasswordPatchResponse>, response: Response<PasswordPatchResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: PasswordPatchResponse = response.body()!!
                result = resp.result!!
                when(resp.code){
                    1000 -> passwordPatchResult.changePasswordSuccess(result)
                    else -> passwordPatchResult.changePasswordFailure()
                }
            }

            override fun onFailure(call: Call<PasswordPatchResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}