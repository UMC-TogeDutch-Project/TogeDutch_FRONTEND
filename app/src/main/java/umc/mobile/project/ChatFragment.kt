package umc.mobile.project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.chat.ChatRoom
import umc.mobile.project.chat.ChatRoomRVAdapter
import umc.mobile.project.chat.ChattingActivity
import umc.mobile.project.databinding.FragmentChatBinding

class ChatFragment: Fragment() {
    var chatRoomList = ArrayList<ChatRoom>()
    lateinit var chatRoomRVAdapter: ChatRoomRVAdapter
    lateinit var binding: FragmentChatBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        initActionBar()
        initRecycler()

        return binding.root

    }

    private fun initRecycler() {
        chatRoomList.apply {
            add(ChatRoom("버거킹 같이 시키실 분 구합니다~", "aeezip", "알겠습니다~", "오후 10시 30분"))
            add(ChatRoom("피자헛 같이 시키실 분 구합니다~", "limbo", "네~", "오후 9시 30분"))
            add(ChatRoom("롯데리아 같이 시키실 분!", "fsdff", "알겠습니다~", "오전 10시 30분"))

            chatRoomRVAdapter = ChatRoomRVAdapter(chatRoomList)
            binding.rvChatroom.adapter = chatRoomRVAdapter
            binding.rvChatroom.layoutManager = LinearLayoutManager(requireContext())

            chatRoomRVAdapter.setItemClickListener(object: ChatRoomRVAdapter.OnItemClickListener{
                override fun onItemClick(chatRoom: ChatRoom) {
                    activity?.let {
                        val intent = Intent(context, ChattingActivity::class.java)
                        startActivity(intent)
                    }
                }
            })

            chatRoomRVAdapter.notifyDataSetChanged()

        }
    }

    private fun initActionBar() {

        binding.mainActionbar.appbarPageNameLeftTv.text = "채팅"

        binding.mainActionbar.appbarBackBtn.setOnClickListener {
            (context as MainActivity).supportFragmentManager
                .popBackStack()
        }

    }
}