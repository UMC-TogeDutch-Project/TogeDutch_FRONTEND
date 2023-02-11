package umc.mobile.project.ram.Auth.Review

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import umc.mobile.project.mypage.GetUser.UserGetResponse
import java.sql.Timestamp
import java.util.*

class ReviewPostService {
    var timestamp = Timestamp(Date().time)
    //var timestamp = Date(System.currentTimeMillis())
    private lateinit var result : Integer

    private lateinit var reviewPostResult : ReviewPostResult

    fun setReviewResult(reviewPostResult: ReviewPostResult){
        this.reviewPostResult = reviewPostResult
    }

    fun sendReview(application_id : Int, review : ReviewPostRequest){
        val authService = getRetrofit().create(ReviewPostRetrofitInterface::class.java)
        authService.sendReview(application_id, review).enqueue(object: Callback<ReviewPostResponse> {
            override fun onResponse(call: Call<ReviewPostResponse>, response: Response<ReviewPostResponse>) {
                Log.d("REVIEW-POST SUCCESS",response.toString())
                val resp : ReviewPostResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                result = resp.result!!
                when(resp.code){
                    1000 -> reviewPostResult.reviewSuccess(result)
                    else -> reviewPostResult.reviewFailure()
                }
            }

            override fun onFailure(call: Call<ReviewPostResponse>, t: Throwable) {
                Log.d("REVIEW-POST FAILURE",t.message.toString())
            }
        })
    }
}