package umc.mobile.project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.announcement.AnnounceDetailActivity
import umc.mobile.project.announcement.AnnounceListActivity
import umc.mobile.project.databinding.FragmentHomeBinding
import umc.mobile.project.news.NewsActivity

class HomeFragment: Fragment() {
    lateinit var dataRVAdapter: DataRVAdapter
    private var _viewBinding: FragmentHomeBinding? = null
    private val viewBinding get() = _viewBinding!!

    private var dummyHomeData = ArrayList<HomeData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentHomeBinding.inflate(inflater, container, false)


        viewBinding.btnNews.setOnClickListener {
            val intent = Intent(getActivity(), NewsActivity::class.java)
            startActivity(intent)

        }

        initRecycler()

        return viewBinding.root



    }

    private fun initRecycler(){
        //데이터
        dummyHomeData.apply {
            add(HomeData.Header("최신순"))
            add(HomeData.Item(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )
            add(HomeData.Item(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )
            add(HomeData.Item(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )

            add(HomeData.Header("마감 임박"))
            add(HomeData.Item(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )
            add(HomeData.Item(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )
            add(HomeData.Item(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345)
            )


        }

        //더미데이터와 리사이클러뷰 연결
        dataRVAdapter = DataRVAdapter(dummyHomeData)

        //리사이클러뷰를 어댑터에 연결
        viewBinding.rvMain.adapter = dataRVAdapter

        viewBinding.rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        dataRVAdapter.setItemClickListener1(object : DataRVAdapter.OnItemClickListener1{
            override fun onItemClick1(item: HomeData.Item) {
                activity?.let {
                    dataRVAdapter.notifyDataSetChanged()
                    val intent = Intent(context, AnnounceDetailActivity::class.java)
                    startActivity(intent)
                }

            }

        })
        dataRVAdapter.notifyDataSetChanged()

        dataRVAdapter.setItemClickListener2(object : DataRVAdapter.OnItemClickListener2{
            override fun onItemClick2(header: HomeData.Header) {
                activity?.let {
                    dataRVAdapter.notifyDataSetChanged()
                    val intent = Intent(context, AnnounceListActivity::class.java)
                    startActivity(intent)
                }

            }

        })
        dataRVAdapter.notifyDataSetChanged()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }



}