package umc.mobile.project.mypage.profile.profileImage

import android.content.ContentValues.TAG
import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.MemberData
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.Date

class profileImagePatchService {
    var timestamp = Timestamp(Date().time)
    //var timestamp = Date(System.currentTimeMillis())
    private var result : ChangeProfileResult = ChangeProfileResult(userIdx = 1, keywordIdx = 1, name =  "", role = "", email = "", password = "",
        phone="", image = "" , status =  "", created_at = timestamp,
        updated_at = timestamp, latitude = 1.0, longitude = 1.0, jwt = "")

    private lateinit var profileImagePatchResult: profileImagePatchResult

    fun setProfileImagePatchResult(profileImagePatchResult: profileImagePatchResult){
        this.profileImagePatchResult = profileImagePatchResult
    }

    fun changeProfile(jwt : String, userIdx : Int, profileImage : MultipartBody.Part){
        val authService = getRetrofit().create(profileImageRetrofitInterface::class.java)
        authService.changeProfileImage(jwt, userIdx, profileImage).enqueue(object: Callback<profileImagePatchResponse> {
            override fun onResponse(call: Call<profileImagePatchResponse>, response: Response<profileImagePatchResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: profileImagePatchResponse = response.body()!!
                result = resp.result!!
                when(resp.code){
                    1000 -> {
                        profileImagePatchResult.profileImageSuccess(resp.code, resp.result!!)
                        Log.d(TAG, "onResponse: ${resp}")
                    }
                    else -> profileImagePatchResult.profileImageFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<profileImagePatchResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}