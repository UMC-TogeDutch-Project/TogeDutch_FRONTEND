package umc.mobile.project.announcement

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.databinding.ActivityAnnounceListBinding

class AnnounceListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnnounceListBinding
    private lateinit var announceRvAdapter: AnnounceRVAdapter
    var mAnnounceData = ArrayList<AnnounceData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnounceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

    }


    private fun initRecyclerView(){
        mAnnounceData.apply{
            add(AnnounceData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(AnnounceData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(AnnounceData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(AnnounceData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(AnnounceData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))

        }
        announceRvAdapter = AnnounceRVAdapter(mAnnounceData)
        binding.recyclerView.adapter=announceRvAdapter //리사이클러뷰에 어댑터 연결
        binding.recyclerView.layoutManager= LinearLayoutManager(this) //레이아웃 매니저 연결
        binding.recyclerView.addItemDecoration(AnnounceRVAdapterDecoration(20))
    }
}