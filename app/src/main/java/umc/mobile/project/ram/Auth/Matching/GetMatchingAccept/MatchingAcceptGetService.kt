package umc.mobile.project.ram.Auth.Matching.GetMatchingAccept

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit

class MatchingAcceptGetService {
    private lateinit var matchingAcceptGetResult: MatchingAcceptGetResult

    fun setMatchingAcceptGetResult(matchingAcceptGetResult: MatchingAcceptGetResult) {
        this.matchingAcceptGetResult = matchingAcceptGetResult
    }

    fun getMatchingAccept(user_id: Int, post_id: Int) {
        val matchingGetService = getRetrofit().create(MatchingAcceptGetRetrofitInterface::class.java)

        matchingGetService.getMatchingAccept(user_id, post_id).enqueue(object : Callback<MatchingAcceptGetResponse> {
            override fun onResponse(call: Call<MatchingAcceptGetResponse>, response: Response<MatchingAcceptGetResponse>, ) {
                Log.d("RANDOMMATCHING-ACCEPT-GET SUCCESS", response.toString())
                val resp: MatchingAcceptGetResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                when (resp.code) {
                    1000 -> matchingAcceptGetResult.getMatchingAcceptSuccess(resp.code, resp.result!!)
                    else -> matchingAcceptGetResult.getMatchingAcceptFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<MatchingAcceptGetResponse>, t: Throwable) {
                Log.d("RANDOMMATCHING-ACCEPT-GET FAILURE", t.message.toString())
            }
        })
    }
}