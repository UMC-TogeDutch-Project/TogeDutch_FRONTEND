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
import umc.mobile.project.databinding.FragmentNewsMateBinding
import umc.mobile.project.news.mate.MateData
import umc.mobile.project.news.mate.MateDataRVAdapter
import umc.mobile.project.news.mate.MateGetResult
import umc.mobile.project.news.mate.MateResponse
import umc.mobile.project.ram.my_application_1.user_id_logined


class NewsMateFragment : Fragment(), MateGetResult{

    private lateinit var viewBinding: FragmentNewsMateBinding

    val userIdx = user_id_logined

    private var dummyMateData = ArrayList<MateData>()

    val TAG: String = "로그"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentNewsMateBinding.inflate(inflater, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsApiService = retrofit.create(NewsApiService::class.java)

        newsApiService.getMate(userIdx).enqueue(object : Callback<MateResponse>{
            override fun onResponse(call: Call<MateResponse>, response: Response<MateResponse>) {
                Log.d(TAG, "onResponse: 통신 성공")
                if(response.isSuccessful){
                    val mateResponseData = response.body()!!
                    Log.d(TAG, "onResponse: ${mateResponseData}")
                    when(mateResponseData.code){
                        1000 -> recordSuccess(mateResponseData.result)
                        3022 -> recordFailure()
                    }
                }
                else{
                    Log.d(TAG, "onResponse: 응답 실패")
                }
            }

            override fun onFailure(call: Call<MateResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: 통신 실패")
            }

        })


//        dummyMateData.apply {
//            add(MateData("2023년 01월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
//            add(MateData("2027년 02월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
//            add(MateData("2023년 03월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
//            add(MateData("2023년 04월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
//            add(MateData("2023년 05월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
//            add(MateData("2023년 06월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
//            add(MateData("2023년 07월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
//            add(MateData("2023년 08월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
//        }
        //더미데이터와 리사이클러뷰 연결


        //리사이클러뷰를 어댑터에 연결


        return viewBinding.root
    }

    override fun recordSuccess(result: ArrayList<MateData>) {
        dummyMateData.addAll(result)
        val mateDataRVAdapter = MateDataRVAdapter(dummyMateData)

        viewBinding.rvMain.adapter = mateDataRVAdapter
        viewBinding.rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    override fun recordFailure() {
        Log.d(TAG, "recordFailure: 또 안돼")
    }


}