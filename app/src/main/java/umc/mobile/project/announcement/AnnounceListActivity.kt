package umc.mobile.project.announcement

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.DataRecentRVAdapter
import umc.mobile.project.DataImminentRVAdapter
import umc.mobile.project.HomeData
import umc.mobile.project.R
import umc.mobile.project.databinding.ActivityAnnounceListBinding
import kotlin.collections.ArrayList

class AnnounceListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnnounceListBinding
    private lateinit var dataRecentRVAdapter: DataRecentRVAdapter
    private lateinit var dataImminentRVAdapter: DataImminentRVAdapter
    var recentAnnounceData = ArrayList<HomeData>()
    var imminentAnnounceData = ArrayList<HomeData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnounceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerViewRecent()


        initActionBar()

        setupSpinnerText()
        setupSpinnerHandler()
    }

    private fun setupSpinnerText() {
        val txt = resources.getStringArray(R.array.spinner_txt2)
        val adapter = ArrayAdapter(this, R.layout.spinner_item_custom, txt)
        binding.spinner.adapter = adapter
    }
    private fun setupSpinnerHandler() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position == 0) { // 최신순 일 때

                    binding.recent.visibility = View.VISIBLE // 최신순 화면 visible
                    binding.imminent.visibility = View.INVISIBLE // 마감임박 화면 invisible

                }

                else{ // 마감임박 순일 때
                    initRecyclerViewImminent()
                    binding.recent.visibility = View.INVISIBLE // 최신순 화면 invisible
                    binding.imminent.visibility = View.VISIBLE // 마감임박 화면 visible

                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }


    private fun initRecyclerViewRecent(){
        recentAnnounceData.apply {
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


            dataRecentRVAdapter = DataRecentRVAdapter(recentAnnounceData)


            binding.rvMainRecent.adapter = dataRecentRVAdapter //리사이클러뷰에 어댑터 연결
            binding.rvMainRecent.layoutManager= LinearLayoutManager(this@AnnounceListActivity) //레이아웃 매니저 연결
            binding.rvMainRecent.addItemDecoration(AnnounceRVAdapterDecoration(20))


        dataRecentRVAdapter.setItemClickListener(object: DataRecentRVAdapter.OnItemClickListener{
                override fun onItemClick(announceData: HomeData) {
                    val dlg = AnnounceListDetailDialog(this@AnnounceListActivity)
                    dlg.start()

                }
            })


            dataRecentRVAdapter.notifyDataSetChanged()


    }
    private fun initRecyclerViewImminent(){
        imminentAnnounceData.apply {
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

        dataImminentRVAdapter = DataImminentRVAdapter(imminentAnnounceData)

        binding.rvMainImminent.adapter = dataImminentRVAdapter //리사이클러뷰에 어댑터 연결
        binding.rvMainImminent.layoutManager= LinearLayoutManager(this@AnnounceListActivity) //레이아웃 매니저 연결
        binding.rvMainImminent.addItemDecoration(AnnounceRVAdapterDecoration(20))


        dataImminentRVAdapter.setItemClickListener(object: DataImminentRVAdapter.OnItemClickListener{
            override fun onItemClick(announceData: HomeData) {
                val dlg = AnnounceListDetailDialog(this@AnnounceListActivity)
                dlg.start()
            }
        })

        dataImminentRVAdapter.notifyDataSetChanged()

    }


    private fun initActionBar() {


        binding.annActionBar.appbarBackBtn.setOnClickListener {
            finish()
        }

    }


//    fun sortCreated():Comparator<HomeData.Item> = object :Comparator<HomeData.Item>{
//        override fun compare(o1: HomeData.Item?, o2: HomeData.Item?): Int {
//
//            return o1!!.created_at!!.compareTo(o2!!.created_at!!)
//        }
//
//    }


    }
