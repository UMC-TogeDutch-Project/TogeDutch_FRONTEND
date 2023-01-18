package umc.mobile.project.announcement

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.databinding.ActivityAnnouncementListBinding

class AnnouncementListActivity(intent: Intent) : AppCompatActivity() {
    private lateinit var binding: ActivityAnnouncementListBinding
    private lateinit var announcementRvAdapter: AnnouncementRVAdapter
    var mAnnouncementData = ArrayList<AnnouncementData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnouncementListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

    }


    private fun initRecyclerView(){
        mAnnouncementData.apply{
            add(AnnouncementData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(AnnouncementData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(AnnouncementData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(AnnouncementData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(AnnouncementData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))

        }
        announcementRvAdapter = AnnouncementRVAdapter(mAnnouncementData)
        binding.recyclerView.adapter=announcementRvAdapter //리사이클러뷰에 어댑터 연결
        binding.recyclerView.layoutManager= LinearLayoutManager(this) //레이아웃 매니저 연결
        binding.recyclerView.addItemDecoration(AnnouncementRVAdapterDecoration(20))
    }
}