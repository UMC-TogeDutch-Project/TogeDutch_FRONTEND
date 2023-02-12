package umc.mobile.project.mypage.profile.Review

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit

class ReviewGetService {
    private lateinit var reviewGetResult: ReviewGetResult

    fun setPostGetResult(reviewGetResult: ReviewGetResult){
        this.reviewGetResult = reviewGetResult
    }

    fun getReview(post_id : Int){
        val reviewGetService = getRetrofit().create(ReviewGetRetrofitInterface::class.java)

        reviewGetService.getReview(post_id).enqueue(object : Callback<ReviewGetResponse> {
            override fun onResponse(call: Call<ReviewGetResponse>, response: Response<ReviewGetResponse>) {
                Log.d("REVIEW-GET SUCCESS",response.toString())
                val resp: ReviewGetResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                when(resp.code) {
                    1000 -> reviewGetResult.getReviewSuccess(resp.code, resp.result!!)
                    else -> reviewGetResult.getReviewFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<ReviewGetResponse>, t: Throwable) {
                Log.d("REVIEW-GET FAILURE",t.message.toString())
            }
        })
    }
}