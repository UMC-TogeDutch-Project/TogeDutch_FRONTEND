package umc.mobile.project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.mobile.project.announcement.AnnounceDetailActivity
import umc.mobile.project.announcement.AnnounceListActivity

import umc.mobile.project.announcement.AnnounceRVAdapterDecoration
import umc.mobile.project.databinding.ActivityAnnounceListBinding
import umc.mobile.project.databinding.FragmentHomeBinding
import umc.mobile.project.news.NewsActivity
import umc.mobile.project.signup.Auth.ApiService

class HomeFragment: Fragment() {
    lateinit var dataRecentRVAdapter: DataRecentRVAdapter
    lateinit var dataImminentRVAdapter: DataImminentRVAdapter
    private var _viewBinding: FragmentHomeBinding? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var binding: ActivityAnnounceListBinding

    private var dummyHomeDataRecent = ArrayList<HomeData>()
    private var dummyHomeDataImminent = ArrayList<HomeData>()
    val TAG: String = "로그"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = ActivityAnnounceListBinding.inflate(inflater, container, false)

        //Retrofit2 선언
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.adsRandom().enqueue(object : Callback<AdsRandomResponse>{
            override fun onResponse(
                call: Call<AdsRandomResponse>,
                response: Response<AdsRandomResponse>
            ) {
                Log.d(TAG, "onResponse: 요청 성공")
                if(response.isSuccessful) {
                    val adsRandomResponseData = response.body()
                    Log.d(TAG, "onResponse: 요청값 정상 ${adsRandomResponseData}")
//                    if(adsRandomResponseData != null){
//                        when(adsRandomResponseData.code){
//                            1000 -> {
//                                viewBinding.tvAdsTitle.text = adsRandomResponseData.result!!.store
//                                viewBinding.tvAdsMessage.text = adsRandomResponseData.result!!.request
//                                Glide.with(this@HomeFragment)
//                                    .load(adsRandomResponseData.result.image)
//                                    .into(viewBinding.imgAds)
//                            }
//                        }
//                    }
                }
            }

            override fun onFailure(call: Call<AdsRandomResponse>, t: Throwable) {
                Log.d(TAG, "onResponse: 요청 실패")
            }

        })




        viewBinding.btnNews.setOnClickListener {
            val intent = Intent(getActivity(), NewsActivity::class.java)
            startActivity(intent)

        }


        viewBinding.btnMoreRcent.setOnClickListener() {
            val intent = Intent(context, AnnounceListActivity::class.java)
            startActivity(intent)
            num1 = 0

        }

        viewBinding.btnMoreImminent.setOnClickListener {

            val intent = Intent(context, AnnounceListActivity::class.java)
            startActivity(intent)
            num1 =1
        }

        initRecyclerViewRecent()
        initRecyclerViewImminent()

        return viewBinding.root
    }

    private fun initRecyclerViewRecent(){
        dummyHomeDataRecent.apply {
            add(HomeData(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )
            add(HomeData(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-16T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )
            add(HomeData(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-17T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )

            add(HomeData(7, "버거킹 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-18T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )
            add(HomeData(7, "버거킹 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-19T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )
            add(HomeData(7, "버거킹 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-20T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )


        }


        dataRecentRVAdapter = DataRecentRVAdapter(dummyHomeDataRecent)


        viewBinding.rvRecent.adapter = dataRecentRVAdapter //리사이클러뷰에 어댑터 연결
        viewBinding.rvRecent.layoutManager= LinearLayoutManager(context) //레이아웃 매니저 연결



        dataRecentRVAdapter.setItemClickListener(object: DataRecentRVAdapter.OnItemClickListener{
            override fun onItemClick(announceData: HomeData) {
                val intent = Intent(context, AnnounceDetailActivity::class.java)
                startActivity(intent)
            }
        })


        dataRecentRVAdapter.notifyDataSetChanged()


    }
    private fun initRecyclerViewImminent(){
        dummyHomeDataImminent.apply {
            add(HomeData(7, "버거킹 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-18T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )
            add(HomeData(7, "버거킹 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-19T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )
            add(HomeData(7, "버거킹 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-20T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )
            add(HomeData(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )
            add(HomeData(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-16T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )
            add(HomeData(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-17T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )




        }

        dataImminentRVAdapter = DataImminentRVAdapter(dummyHomeDataImminent)

        viewBinding.rvImminent.adapter = dataImminentRVAdapter //리사이클러뷰에 어댑터 연결
        viewBinding.rvImminent.layoutManager= LinearLayoutManager(context) //레이아웃 매니저 연결


        dataImminentRVAdapter.setItemClickListener(object: DataImminentRVAdapter.OnItemClickListener{
            override fun onItemClick(announceData: HomeData) {
                val intent = Intent(context, AnnounceDetailActivity::class.java)
                startActivity(intent)
            }
        })

        dataImminentRVAdapter.notifyDataSetChanged()

    }





    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
    companion object{
        var num1 = 0
    }


}