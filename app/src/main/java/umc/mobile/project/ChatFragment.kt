package umc.mobile.project

import Post
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.ram.chat.ChatRoom
import umc.mobile.project.ram.chat.ChatRoomRVAdapter
import umc.mobile.project.ram.chat.ChattingActivity
import umc.mobile.project.databinding.FragmentChatBinding
import umc.mobile.project.ram.Auth.Application.GetUser.UserGetService
import umc.mobile.project.ram.Auth.ChatRoom.ChatRoomGetList.ChatRoomList
import umc.mobile.project.ram.Auth.ChatRoom.ChatRoomGetList.ChatRoomListGetResult
import umc.mobile.project.ram.Auth.ChatRoom.ChatRoomGetList.ChatRoomListGetService
import umc.mobile.project.ram.Auth.Post.GetPostJoin.PostJoinGetResult
import umc.mobile.project.ram.Auth.Post.GetPostJoin.PostJoinGetService
import umc.mobile.project.ram.chat.post_id_chatroom
import umc.mobile.project.ram.my_application_1.user_id_logined

class ChatFragment : Fragment(), ChatRoomListGetResult {
    var chatRoomList = ArrayList<ChatRoomList>()
    lateinit var chatRoomRVAdapter: ChatRoomRVAdapter
    lateinit var binding: FragmentChatBinding

    val TAG: String = "로그"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        Log.d(TAG, "onCreateView: ChatTest Log")

        initActionBar()
        initRecycler()

        return binding.root

    }

    private fun initRecycler() {
        val chatRoomListGetService = ChatRoomListGetService()
        chatRoomListGetService.setChatRoomListGetResult(this)
        chatRoomListGetService.getChatRoomUpload(user_id_logined)
    }

    private fun initActionBar() {

        binding.mainActionbar.appbarPageNameLeftTv.text = "채팅"

        binding.mainActionbar.appbarBackBtn.setOnClickListener {
            (context as MainActivity).supportFragmentManager
                .popBackStack()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun getChatRoomListSuccess(code: Int, result: ArrayList<ChatRoomList>) {
        chatRoomList.addAll(result)
        chatRoomRVAdapter = ChatRoomRVAdapter(chatRoomList)
        binding.rvChatroom.adapter = chatRoomRVAdapter
        binding.rvChatroom.layoutManager = LinearLayoutManager(requireContext())

        chatRoomRVAdapter.setItemClickListener(object : ChatRoomRVAdapter.OnItemClickListener {
            override fun onItemClick(chatRoom: ChatRoomList) {
                activity?.let {
                    val intent = Intent(context, ChattingActivity::class.java)
                    intent.putExtra("chatRoom_id", chatRoom.chatRoomIdx!!)
                    startActivity(intent)
                }
            }
        })

        chatRoomRVAdapter.notifyDataSetChanged()
    }

    override fun getChatRoomListFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }


}