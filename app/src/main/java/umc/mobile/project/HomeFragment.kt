package umc.mobile.project

import Post
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import umc.mobile.project.announcement.AnnounceDetailActivity

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import umc.mobile.project.announcement.AnnounceListActivity
import umc.mobile.project.announcement.Auth.PostImminentGet.PostImminentGetResult
import umc.mobile.project.announcement.Auth.PostImminentGet.PostImminentGetService
import umc.mobile.project.announcement.Auth.PostRecentGet.PostRecentGetResult
import umc.mobile.project.announcement.Auth.PostRecentGet.PostRecentGetService
import umc.mobile.project.databinding.FragmentHomeBinding
import umc.mobile.project.news.NewsActivity
import umc.mobile.project.ram.my_application_1.user_id_logined
import umc.mobile.project.ram.my_application_1.user_id_var
import umc.mobile.project.search.SearchActivity
import umc.mobile.project.wishlist.GetLikePost.LikePostGetResult
import umc.mobile.project.wishlist.GetLikePost.LikePostGetService

class HomeFragment: Fragment(), PostRecentGetResult, PostImminentGetResult, LikePostGetResult {
    lateinit var dataRecentRVAdapter: DataRecentRVAdapter
    lateinit var dataImminentRVAdapter: DataImminentRVAdapter
    private lateinit var viewBinding: FragmentHomeBinding

    val TAG: String = "로그"
//    private var _viewBinding: FragmentHomeBinding? = null
//    private val viewBinding get() = _viewBinding!!
//
//    private var dummyHomeDataRecent = ArrayList<HomeData>()
//    private var dummyHomeDataImminent = ArrayList<HomeData>()

    private var postList = ArrayList<Post>()
    private var postList1 = ArrayList<Post>()

    var like_list = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)

//        _viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
//        binding = ActivityAnnounceListBinding.inflate(inflater, container, false)

        Log.d(TAG, "onCreateView: TestLog")

        //Retrofit2 선언
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(AdsRandomApiService::class.java)
        var adsImage: String

        apiService.adsRandom()?.enqueue(object : Callback<AdsRandomResponse> {
            override fun onResponse(
                call: Call<AdsRandomResponse>,
                response: Response<AdsRandomResponse>,
            ) {
                Log.d(TAG, "onResponse:adsRandom요청 성공")
                if(response.isSuccessful) {
                    val adsRandomResponseData = response.body()
                    Log.d(TAG, "onResponse:adsRandom요청값 정상 ${adsRandomResponseData}")
                    if(adsRandomResponseData != null){
                        Log.d(TAG, "onResponse:..nullCheck")

                        when(adsRandomResponseData.code){
                            1000 -> {
                                Log.d(TAG, "onResponse:1000번 진입")
//                                viewBinding.tvAdsTitle.text = adsRandomResponseData.result!!.store
//                                viewBinding.tvAdsMessage.text = adsRandomResponseData.result.request
//                                adsImage = adsRandomResponseData.result.image
//                                Log.d(TAG, "onResponse: ${adsImage}")

//                                Glide.with(Fragment())
//                                    .load(adsImage)
//                                    .into(viewBinding.imgAds)
                            }
                        }
                    }
                    else{

                    }
                }
                else{

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

        viewBinding.btnInputAlarm.setOnClickListener {
            Log.d(TAG, "onCreateView: 검색버튼 클릭")
            if(viewBinding.etInputAlarmKeyword.text.toString() == ""){
                Toast.makeText(getActivity(), "검색어를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(getActivity(), SearchActivity::class.java)
                var search : String = viewBinding.etInputAlarmKeyword.text.toString()
                intent.putExtra("search", search)
                startActivity(intent)
            }
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


//        initRecyclerViewRecent()
//        initRecyclerViewImminent()


        return viewBinding.root



    }

    override fun onResume() {
        super.onResume()
        Log.d("프래그먼트 재호출" ,"")
        getLikePost()
        initRecyclerViewRecent()
        initRecyclerViewImminent()

    }
    private fun initRecyclerViewRecent(){
        getPostLatest()

    }
    private fun initRecyclerViewImminent(){
        getPostImminent()

    }

    private fun getPostLatest(){
        val postRecentGetService = PostRecentGetService()
        postRecentGetService.setPostGetResult(this)
        postRecentGetService.getPost( )

    }
    private fun getPostImminent(){
        val postImminentGetService = PostImminentGetService()
        postImminentGetService.setPostGetResult(this)
        postImminentGetService.getPost()

    }




    override fun recordSuccess(result: ArrayList<Post>) {
//        Toast.makeText(this, "공고 등록 성공.", Toast.LENGTH_SHORT).show()
//        finish()
        Log.d("success 호출","")
//        postList.addAll(result)
//        println("postList 가장 최근에 넣은 값 : " + postList[0].title)
//        dataRecentRVAdapter = DataRecentRVAdapter(postList)
        dataRecentRVAdapter = DataRecentRVAdapter(result, like_list)

        println("가장 최근에 넣은 값 : " + result[0].title)

        viewBinding.rvRecent.adapter = dataRecentRVAdapter //리사이클러뷰에 어댑터 연결
        viewBinding.rvRecent.layoutManager= LinearLayoutManager(context) //레이아웃 매니저 연결

        dataRecentRVAdapter.setItemClickListener(object: DataRecentRVAdapter.OnItemClickListener{
            override fun onItemClick(announceData: Post) {
                val intent = Intent(context, AnnounceDetailActivity::class.java)
                startActivity(intent)
            }
        })

        dataRecentRVAdapter.notifyDataSetChanged()
    }

    override fun recordFailure() {
        TODO("Not yet implemented")
    }




    override fun recordSuccess1(result: ArrayList<Post>) {
        postList1.addAll(result)
        dataImminentRVAdapter = DataImminentRVAdapter(postList1)
        viewBinding.rvImminent.adapter = dataImminentRVAdapter //리사이클러뷰에 어댑터 연결
        viewBinding.rvImminent.layoutManager= LinearLayoutManager(context) //레이아웃 매니저 연결


        dataImminentRVAdapter.setItemClickListener(object: DataImminentRVAdapter.OnItemClickListener{
            override fun onItemClick(announceData: Post) {
                val intent = Intent(context, AnnounceDetailActivity::class.java)
                startActivity(intent)
            }
        })

        dataImminentRVAdapter.notifyDataSetChanged()
    }

    override fun recordFailure1() {
        TODO("Not yet implemented")
    }
    companion object{
        var num1 = 0
    }
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _viewBinding = null
//    }

    private fun getLikePost() {
        val likePostGetService = LikePostGetService()
        likePostGetService.setLikePostGetResult(this)
        likePostGetService.getLikePost(user_id_logined)
        user_id_var = user_id_logined // 상세목록 볼 때 현재 로그인된 유저를 보여줄 수 있게 덮어씌워주기

    }

    override fun getPostUploadSuccess(code: Int, result: ArrayList<Post>) {
        like_list = result
        //Toast.makeText(this, "관심목록 불러오기 성공", Toast.LENGTH_SHORT).show()
    }

    override fun getPostUploadFailure(code: Int, message: String) {
        Toast.makeText(context, "관심목록 불러오기 실패", Toast.LENGTH_SHORT).show()
    }
}