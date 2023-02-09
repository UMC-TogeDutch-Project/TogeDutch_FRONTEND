package umc.mobile.project.mypage.ChangePhoneNumber

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class PhoneNumberPatchService {
    var timestamp = Timestamp(Date().time)
    //var timestamp = Date(System.currentTimeMillis())
    private var result : ChangePhoneNumberResult = ChangePhoneNumberResult(userIdx = 1, keywordIdx = 1, name =  "", role = "", email = "", password = "",
        phone="", image = "" , status =  "", created_at = timestamp,
        updated_at = timestamp, latitude = 1.0, longitude = 1.0, jwt = "")

    private lateinit var phoneNumberPatchResult: PhoneNumberPatchResult

    fun setPhoneNumberPatchResult(phoneNumberPatchResult: PhoneNumberPatchResult){
        this.phoneNumberPatchResult = phoneNumberPatchResult
    }

    fun changePhoneNumber(jwt : String, userIdx : Int, phoneNumber : PhoneNumberPatchRequest){
        val authService = getRetrofit().create(PhoneNumberPatchRetrofitInterface::class.java)
        authService.changePhoneNumber(jwt, userIdx, phoneNumber).enqueue(object: Callback<PhoneNumberPatchResponse> {
            override fun onResponse(call: Call<PhoneNumberPatchResponse>, response: Response<PhoneNumberPatchResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: PhoneNumberPatchResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                result = resp.result!!
                when(resp.code){
                    1000 -> phoneNumberPatchResult.changePhoneNumberSuccess(result)
                    else -> phoneNumberPatchResult.changePhoneNumberFailure()
                }
            }

            override fun onFailure(call: Call<PhoneNumberPatchResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}