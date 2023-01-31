package umc.mobile.project

import Post
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.announcement.AnnounceListActivity
import umc.mobile.project.announcement.Auth.PostImminentGet.PostImminentGetResult
import umc.mobile.project.announcement.Auth.PostImminentGet.PostImminentGetService
import umc.mobile.project.announcement.Auth.PostRecentGet.PostRecentGetResult
import umc.mobile.project.announcement.Auth.PostRecentGet.PostRecentGetService
import umc.mobile.project.databinding.FragmentHomeBinding
import umc.mobile.project.news.NewsActivity

class HomeFragment: Fragment(), PostRecentGetResult, PostImminentGetResult {
    lateinit var dataRecentRVAdapter: DataRecentRVAdapter
    lateinit var dataImminentRVAdapter: DataImminentRVAdapter
    private var _viewBinding: FragmentHomeBinding? = null
    private val viewBinding get() = _viewBinding!!
//
//    private var dummyHomeDataRecent = ArrayList<HomeData>()
//    private var dummyHomeDataImminent = ArrayList<HomeData>()

    private var postList = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
//        binding = ActivityAnnounceListBinding.inflate(inflater, container, false)


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
        getPostLatest()
//        dummyHomeDataRecent.apply {
//            add(HomeData(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
//                2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
//            )
//            add(HomeData(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
//                2, 1, "모집중", "2023-01-16T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
//            )
//            add(HomeData(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
//                2, 1, "모집중", "2023-01-17T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
//            )
//
//            add(HomeData(7, "버거킹 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
//                2, 1, "모집중", "2023-01-18T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
//            )
//            add(HomeData(7, "버거킹 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
//                2, 1, "모집중", "2023-01-19T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
//            )
//            add(HomeData(7, "버거킹 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
//                2, 1, "모집중", "2023-01-20T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
//            )
//
//
//        }


//        dataRecentRVAdapter = DataRecentRVAdapter(dummyHomeDataRecent)
//
//
//        viewBinding.rvRecent.adapter = dataRecentRVAdapter //리사이클러뷰에 어댑터 연결
//        viewBinding.rvRecent.layoutManager= LinearLayoutManager(context) //레이아웃 매니저 연결
//
//
//
//        dataRecentRVAdapter.setItemClickListener(object: DataRecentRVAdapter.OnItemClickListener{
//            override fun onItemClick(announceData: HomeData) {
//                val intent = Intent(context, AnnounceDetailActivity::class.java)
//                startActivity(intent)
//            }
//        })
//
//
//        dataRecentRVAdapter.notifyDataSetChanged()


    }
    private fun initRecyclerViewImminent(){
        getPostImminent()
//        dummyHomeDataImminent.apply {
//            add(HomeData(7, "버거킹 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
//                2, 1, "모집중", "2023-01-18T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
//            )
//            add(HomeData(7, "버거킹 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
//                2, 1, "모집중", "2023-01-19T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
//            )
//            add(HomeData(7, "버거킹 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
//                2, 1, "모집중", "2023-01-20T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
//            )
//            add(HomeData(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
//                2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
//            )
//            add(HomeData(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
//                2, 1, "모집중", "2023-01-16T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
//            )
//            add(HomeData(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
//                2, 1, "모집중", "2023-01-17T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
//            )
//
//
//
//
//        }

//        dataImminentRVAdapter = DataImminentRVAdapter(dummyHomeDataImminent)
//
//        viewBinding.rvImminent.adapter = dataImminentRVAdapter //리사이클러뷰에 어댑터 연결
//        viewBinding.rvImminent.layoutManager= LinearLayoutManager(context) //레이아웃 매니저 연결
//
//
//        dataImminentRVAdapter.setItemClickListener(object: DataImminentRVAdapter.OnItemClickListener{
//            override fun onItemClick(announceData: HomeData) {
//                val intent = Intent(context, AnnounceDetailActivity::class.java)
//                startActivity(intent)
//            }
//        })
//
//        dataImminentRVAdapter.notifyDataSetChanged()

    }

    private fun getPostLatest(){
        val postRecentGetService = PostRecentGetService()
        postRecentGetService.setPostGetResult(this)
        postRecentGetService.getPost("latest" )

    }
    private fun getPostImminent(){
        val postImminentGetService = PostImminentGetService()
        postImminentGetService.setPostGetResult(this)
        postImminentGetService.getPost("imminent" )

    }




    override fun recordSuccess(result: ArrayList<Post>) {
//        Toast.makeText(this, "공고 등록 성공.", Toast.LENGTH_SHORT).show()
//        finish()
        postList.addAll(result)
        dataRecentRVAdapter = DataRecentRVAdapter(postList)
        dataImminentRVAdapter = DataImminentRVAdapter(postList)


        viewBinding.rvRecent.adapter = dataRecentRVAdapter //리사이클러뷰에 어댑터 연결
        viewBinding.rvRecent.layoutManager= LinearLayoutManager(context) //레이아웃 매니저 연결

        viewBinding.rvImminent.adapter = dataImminentRVAdapter //리사이클러뷰에 어댑터 연결
        viewBinding.rvImminent.layoutManager= LinearLayoutManager(context) //레이아웃 매니저 연결



//        dataRecentRVAdapter.setItemClickListener(object: DataRecentRVAdapter.OnItemClickListener{
//            override fun onItemClick(announceData: Post) {
//                val intent = Intent(context, AnnounceDetailActivity::class.java)
//                startActivity(intent)
//            }
//        })
//
//
//        dataRecentRVAdapter.notifyDataSetChanged()
    }

    override fun recordFailure() {
        TODO("Not yet implemented")
    }


    companion object{
        var num1 = 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}