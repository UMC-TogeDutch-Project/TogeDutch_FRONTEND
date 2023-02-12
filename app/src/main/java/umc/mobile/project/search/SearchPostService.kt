package umc.mobile.project.search

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class SearchPostService {
    val TAG: String = "로그"
    var timestamp = Timestamp(Date().time)
    //var timestamp = Date(System.currentTimeMillis())


    private lateinit var searchPostResult : SearchPostResult

    fun setSearchPostResult(searchPostResult: SearchPostResult){
        this.searchPostResult = searchPostResult
    }

    fun searchPostByKeyword(keyword : String?){

        if(keyword == null)
            Log.d(TAG, "검색어를 입력하세요 ==========================")

        val searchService = getRetrofit().create(SearchPostServiceInterface::class.java)
        searchService.searchPostByKeyword(keyword).enqueue(object: Callback<SearchPostResponse> {
            override fun onResponse(call: Call<SearchPostResponse>, response: Response<SearchPostResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val searchPostResponseData: SearchPostResponse = response.body()!!

                when(searchPostResponseData.code){
                    1000 -> searchPostResult.searchSuccess(searchPostResponseData.result)
                    2042 -> Log.d(TAG, "검색어를 입력하세요 ==========================")
                    else -> searchPostResult.searchFailure()
                }
            }

            override fun onFailure(call: Call<SearchPostResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}