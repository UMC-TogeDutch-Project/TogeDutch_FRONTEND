package umc.mobile.project.mypage.withdrawal

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import umc.mobile.project.mypage.ChangePassword.*
import java.sql.Timestamp
import java.util.*

class WithdrawalService {
    var timestamp = Timestamp(Date().time)
    //var timestamp = Date(System.currentTimeMillis())
    private var result : ChangeUserStatusResult = ChangeUserStatusResult(userIdx = 1, keywordIdx = 1, name =  "", role = "", email = "", password = "",
        phone="", image = "" , status =  "", created_at = timestamp,
        updated_at = timestamp, latitude = 1.0, longitude = 1.0, jwt = "")

    private lateinit var withdrawalResult: WithdrawalResult

    fun setWithdrawalResult(withdrawalResult: WithdrawalResult){
        this.withdrawalResult = withdrawalResult
    }

    fun changeUserStatus(jwt : String, userIdx : Int){
        val withdrawalService = getRetrofit().create(WithdrawalServiceRetrofitInterface::class.java)
        withdrawalService.changeUserStatus(jwt, userIdx).enqueue(object:
            Callback<WithdrawalResponse> {
            override fun onResponse(call: Call<WithdrawalResponse>, response: Response<WithdrawalResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: WithdrawalResponse = response.body()!!
                result = resp.result!!
                when(resp.code){
                    1000 -> withdrawalResult.changeUserStatusSuccess(result)
                    else -> withdrawalResult.changeUserStatusFailure()
                }
            }

            override fun onFailure(call: Call<WithdrawalResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}