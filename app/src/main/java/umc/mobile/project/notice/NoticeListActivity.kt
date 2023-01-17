package umc.mobile.project.notice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.databinding.ActivityNoticeListBinding

class NoticeListActivity(intent: Intent) : AppCompatActivity() {
    private lateinit var binding: ActivityNoticeListBinding
    private lateinit var noticeRvAdapter: NoticeRVAdapter
    var mNoticeData = ArrayList<NoticeData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

    }


    private fun initRecyclerView(){
        mNoticeData.apply{
            add(NoticeData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(NoticeData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(NoticeData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(NoticeData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(NoticeData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))

        }
        noticeRvAdapter = NoticeRVAdapter(mNoticeData)
        binding.recyclerView.adapter=noticeRvAdapter //리사이클러뷰에 어댑터 연결
        binding.recyclerView.layoutManager= LinearLayoutManager(this) //레이아웃 매니저 연결
        binding.recyclerView.addItemDecoration(NoticeRVAdapterDecoration(20))
    }
}