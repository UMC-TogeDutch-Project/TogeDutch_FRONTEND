package umc.mobile.project.commercial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.databinding.ActivityNoticeListBinding
import umc.mobile.project.notice.NoticeRVAdapterDecoration

class CommercialListActivity: AppCompatActivity() {
    private lateinit var binding: ActivityNoticeListBinding
    private lateinit var commercialRVAdapter: CommercialRVAdapter
    var mCommercialData = ArrayList<CommercialData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

    }


    private fun initRecyclerView(){
        mCommercialData.apply{
            add(CommercialData("2023년 1월 17일 오후 6시 21분 ","가게이름","000님 광고 신청이 접수되었습니다."))

        }
        commercialRVAdapter = CommercialRVAdapter(mCommercialData)
        binding.recyclerView.adapter=commercialRVAdapter //리사이클러뷰에 어댑터 연결
        binding.recyclerView.layoutManager= LinearLayoutManager(this) //레이아웃 매니저 연결
        binding.recyclerView.addItemDecoration(NoticeRVAdapterDecoration(20))
    }
}