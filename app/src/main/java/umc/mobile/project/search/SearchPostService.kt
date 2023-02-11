package umc.mobile.project.search

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class SearchPostService {
    var TAG = "SearchPostService"
    var timestamp = Timestamp(Date().time)
    //var timestamp = Date(System.currentTimeMillis())
    private var result : Result = Result(post_id = 1, title = "", url =  "", delivery_tips = 1, minimum = 1, order_time = "timestamp",
        num_of_recruits =1 , recruited_num = 1, status =  "", created_at = timestamp,
        updated_at = timestamp, user_id = 1, image = "", latitude = 1.0, longitude = 1.0, chatRoom_id = 1, category = "")

    private lateinit var searchPostResult : SearchPostResult

    fun setSearchPostResult(searchPostResult: SearchPostResult){
        this.searchPostResult = searchPostResult
    }

    fun searchPostByKeyword(keyword : String){

        if(keyword == null)
            Log.d(TAG, "검색어를 입력하세요 ==========================")

        val searchService = getRetrofit().create(SearchPostServiceInterface::class.java)
        searchService.searchPostByKeyword(keyword).enqueue(object: Callback<SearchPostResponse> {
            override fun onResponse(call: Call<SearchPostResponse>, response: Response<SearchPostResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: SearchPostResponse = response.body()!!
                result = resp.result!!
                when(resp.code){
                    1000 -> searchPostResult.searchSuccess(result)
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