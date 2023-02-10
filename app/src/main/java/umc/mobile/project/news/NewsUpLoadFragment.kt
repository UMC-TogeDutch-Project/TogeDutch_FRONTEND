package umc.mobile.project.news


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.mobile.project.R
import umc.mobile.project.databinding.FragmentNewsUploadBinding

class NewsUpLoadFragment : Fragment(){

    private lateinit var viewBiding: FragmentNewsUploadBinding

    //더미데이터 리스트
    private var dummyUpLoadData = ArrayList<UpLoadData>()
    val TAG: String = "로그"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBiding = FragmentNewsUploadBinding.inflate(inflater, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsApiService = retrofit.create(NewsApiService::class.java)

        Log.d(TAG, "onCreateView: 실행")

        val category1 : String? = "닭꼬치"
        val category2 : String? = "떡볶이"
        val category3 : String? = "짜장면"
        val category4 : String? = null
        val category5 : String? = null
        val category6 : String? = null

        newsApiService.getPostFromCategory("닭꼬치", "떡볶이", null, null, null, null, 37.606, 127.036).enqueue(object : Callback<UpLoadResponse>{
            override fun onResponse(
                call: Call<UpLoadResponse>,
                response: Response<UpLoadResponse>
            ) {
                Log.d(TAG, "onResponse:통신성공")
                if(response.isSuccessful){
                    val upLoadResponseData = response.body()
                    Log.d(TAG, "onResponse: ${upLoadResponseData}")
                    if(upLoadResponseData != null){
                        when(upLoadResponseData.code){
                            1000 -> Log.d(TAG, "onResponse: ${upLoadResponseData.result}")

                        }
                    }
                    else{
                        Log.d(TAG, "onResponse: null check 실패")
                    }
                }
                else{
                    if (response.code() == 400) {
                        Log.v("Error code 400",response.errorBody()!!.string());
                    }
                    Log.d(TAG, "onResponse: ${response.code()}")
                    val upLoadResponseData = response.body()
                    Log.d(TAG, "onResponse:body: ${upLoadResponseData}")
                }

            }
            override fun onFailure(call: Call<UpLoadResponse>, t: Throwable) {
                Log.d(TAG, "onFailure:통신실패 ${t}")
            }

        })

        //더미데이터
        dummyUpLoadData.apply {
            add(UpLoadData("2023년 01월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 02월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 03월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 04월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 05월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 06월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 07월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 08월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 09월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 01월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
        }

        //더미데이터와 리사이클러뷰 연결
        val upLoadDataRVAdapter = UpLoadDataRVAdapter(dummyUpLoadData)

        //리사이클러뷰를 어댑터에 연결
        viewBiding.rvMain.adapter = upLoadDataRVAdapter

        viewBiding.rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)



        return viewBiding.root
    }


}