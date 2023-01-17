package umc.mobile.project.my_application_1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.MainActivity
import umc.mobile.project.chat.ChatRoom
import umc.mobile.project.chat.ChatRoomRVAdapter
import umc.mobile.project.chat.ChattingActivity
import umc.mobile.project.databinding.ActivityCurrentapplicationBinding
import umc.mobile.project.databinding.ActivityMyapplicationBinding

class CurrentApplicationActivity : AppCompatActivity() {
    lateinit var binding: ActivityCurrentapplicationBinding
    lateinit var currentRVAdapter: CurrentRVAdapter
    val currentList = ArrayList<CurrentData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentapplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        initRecycler()
    }
    private fun initRecycler() {
        currentList.apply {
            add(CurrentData("2022년 12월 29일 오후 6시 35분", "버거킹 같이 시키실 분 구합니다~", "홈런볼", "님이 메이트를 신청하셨습니다."))
            add(CurrentData("2022년 12월 29일 오후 6시 40분", "피자헛 같이 시키실 분 구합니다~", "쿠키", "님이 메이트를 신청하셨습니다."))


            currentRVAdapter = CurrentRVAdapter(currentList)
            binding.rvApplication.adapter = currentRVAdapter

//            currentRVAdapter.setItemClickListener(object: CurrentRVAdapter.OnItemClickListener{
//                override fun onItemClick(chatRoom: ChatRoom) {
//
//                }
//            })

            currentRVAdapter.notifyDataSetChanged()

        }
    }

    private fun initActionBar() {

        binding.mainActionbar.appbarPageNameLeftTv.text = "공고 신청 현황"

        binding.mainActionbar.appbarBackBtn.setOnClickListener {
             finish()
        }

    }

}