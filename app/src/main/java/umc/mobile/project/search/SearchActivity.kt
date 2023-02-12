package umc.mobile.project.search

import Post
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.mobile.project.AdsRandomApiService
import umc.mobile.project.DataImminentRVAdapter
import umc.mobile.project.announcement.AnnounceDetailActivity
import umc.mobile.project.announcement.Auth.PostRecentGet.PostRecentGetService

import umc.mobile.project.databinding.ActivitySearchBinding
import umc.mobile.project.news.mate.MateDataRVAdapter

class SearchActivity : AppCompatActivity(), SearchPostResult{

    private lateinit var viewBinding: ActivitySearchBinding
    val TAG: String = "로그"

    private var dummySearchData = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivitySearchBinding.inflate(layoutInflater)

        var search = intent.getStringExtra("search")

        viewBinding.etInputAlarmKeyword.setText(search)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(SearchPostServiceInterface::class.java)

        apiService.searchPostByKeyword(viewBinding.etInputAlarmKeyword.text.toString()).enqueue(object : Callback<SearchPostResponse>{
            override fun onResponse(
                call: Call<SearchPostResponse>,
                response: Response<SearchPostResponse>
            ) {
                Log.d(TAG, "onResponse: 응답성공")
                if(response.isSuccessful){
                    val searchPostResponseData = response.body()
                    Log.d(TAG, "onResponse: ${searchPostResponseData}")
                    when(searchPostResponseData!!.code){
                        1000 -> {
                            searchSuccess(searchPostResponseData.result)
                            viewBinding.rvMain.adapter?.notifyDataSetChanged()
                        }
                        2042 -> searchFailure()
                        else -> searchFailure()
                    }
                }
                else{
                    Log.d(TAG, "onResponse: 응답 실패")
                }
            }

            override fun onFailure(call: Call<SearchPostResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        viewBinding.btnInputAlarm.setOnClickListener {
            Log.d(TAG, "onCreateView: 검색버튼 클릭")
            if(viewBinding.etInputAlarmKeyword.text.toString() == ""){
                Toast.makeText(this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                apiService.searchPostByKeyword(viewBinding.etInputAlarmKeyword.text.toString()).enqueue(object : Callback<SearchPostResponse>{
                    override fun onResponse(
                        call: Call<SearchPostResponse>,
                        response: Response<SearchPostResponse>
                    ) {
                        Log.d(TAG, "onResponse: 응답성공")
                        if(response.isSuccessful){
                            val searchPostResponseData = response.body()
                            Log.d(TAG, "onResponse: ${searchPostResponseData}")
                            when(searchPostResponseData!!.code){
                                1000 -> {
                                    dummySearchData.clear()
                                    searchSuccess(searchPostResponseData.result)
                                    viewBinding.rvMain.adapter?.notifyDataSetChanged()
                                }
                                2042 -> searchFailure()
                                else -> searchFailure()
                            }
                        }
                        else{
                            Log.d(TAG, "onResponse: 응답 실패")
                        }
                    }

                    override fun onFailure(call: Call<SearchPostResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }





        viewBinding.btnBack.setOnClickListener {
            finish()
        }

        setContentView(viewBinding.root)
    }

    override fun searchSuccess(result: ArrayList<Post>) {
        dummySearchData.addAll(result)
        val searchDataRVAdapter = SearchDataRVAdapter(dummySearchData)

        viewBinding.rvMain.adapter = searchDataRVAdapter
        viewBinding.rvMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        searchDataRVAdapter.setItemClickListener(object: SearchDataRVAdapter.OnItemClickListener{
            override fun onItemClick(announceData: Post) {
                val intent = Intent(this@SearchActivity, AnnounceDetailActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun searchFailure() {
        TODO("Not yet implemented")
    }
}

