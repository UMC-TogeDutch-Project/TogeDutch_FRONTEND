package umc.mobile.project.ram.Auth.Matching.GetMatching

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import umc.mobile.project.ram.my_application_1.MyPostRVAdapter

class MatchingGetService {
    private lateinit var matchingGetResult: MatchingGetResult

    fun setMatchingGetResult(matchingGetResult: MatchingGetResult) {
        this.matchingGetResult = matchingGetResult
    }

    fun getRandomMatching(post_id: Int) {
        val matchingGetService = getRetrofit().create(MatchingGetRetrofitInterfaces::class.java)

        matchingGetService.getMatching(post_id).enqueue(object : Callback<MatchingGetResponse> {
            override fun onResponse(call: Call<MatchingGetResponse>, response: Response<MatchingGetResponse>, ) {
                Log.d("RANDOMMATCHING-GET SUCCESS", response.toString())
                val resp: MatchingGetResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                when (resp.code) {
//                    1000 ->
                    1000 -> matchingGetResult.getMatchingSuccess(resp.code, resp.result!!)
                    else -> matchingGetResult.getMatchingFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<MatchingGetResponse>, t: Throwable) {
                Log.d("RANDOMMATCHING-GET FAILURE", t.message.toString())
            }
        })
    }
}