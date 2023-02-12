package umc.mobile.project.ram.chat

import Post
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.databinding.ItemChatRoomBinding
import umc.mobile.project.ram.Auth.Application.GetUser.UserGet
import umc.mobile.project.ram.Auth.Application.GetUser.UserGetResult
import umc.mobile.project.ram.Auth.Application.GetUser.UserGetService
import umc.mobile.project.ram.Auth.Chat.ChatAllGet.ChatAllGetResult
import umc.mobile.project.ram.Auth.Chat.ChatAllGet.ChatAllGetService
import umc.mobile.project.ram.Auth.ChatRoom.ChatRoomGetList.ChatRoomList
import umc.mobile.project.ram.Auth.Post.GetPostAll.PostGetAllResult
import umc.mobile.project.ram.Auth.Post.GetPostAll.PostGetAllService
import umc.mobile.project.ram.Auth.Post.GetPostChatIdx.PostChatIdxGetResult
import umc.mobile.project.ram.Auth.Post.GetPostChatIdx.PostChatIdxGetService
import umc.mobile.project.ram.Auth.Post.GetPostJoin.PostJoinGetResult
import umc.mobile.project.ram.Auth.Post.GetPostJoin.PostJoinGetService
import umc.mobile.project.ram.Auth.Post.GetPostUpload.PostUploadGetService
import umc.mobile.project.ram.my_application_1.Timestamp_to_SDF
import umc.mobile.project.ram.my_application_1.user_id_logined

var post_id_chatroom = 0
var user_id_chatroom = 0
var chatRoom_selected_subject = ""

@RequiresApi(Build.VERSION_CODES.O)
class ChatRoomRVAdapter(private val chatRoomList: ArrayList<ChatRoomList>) :
    RecyclerView.Adapter<ChatRoomRVAdapter.ViewHolder>() {
    lateinit var context: Context
    var chatRoom_id: Int = 0
    var chatRoom_id_list = ArrayList<Int>()
    var isFirst = true
    var previous_found_chatRoom_id = 0 // 이전에 찾은 chatRoom_id
    var previous_found_index = 0
    var found_post_index = 0

    var user_id = 0

    // 아이템 레이아웃 결합
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemChatRoomBinding = ItemChatRoomBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )
        context = viewGroup.context
        return ViewHolder(binding)
    }

    // 아이템 개수
    override fun getItemCount(): Int = chatRoomList.size

    // view에 내용 입력
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chatRoomList[position])
        holder.itemView.setOnClickListener {
            chatRoom_id = chatRoomList[position].chatRoomIdx
            itemClickListener.onItemClick(chatRoomList[position])
            notifyItemChanged(position)
        }
    }

    // 레이아웃 내 view 연결
    inner class ViewHolder(val binding: ItemChatRoomBinding) :
        RecyclerView.ViewHolder(binding.root), ChatAllGetResult, UserGetResult, PostChatIdxGetResult {
        //        PostJoinGetResult, UserGetResult,
        fun bind(chatRoom: ChatRoomList) {
            chatRoom_id_list.add(chatRoom.chatRoomIdx)
            chatRoom_selected_subject = binding.itemSubjectTxt.text.toString() // 선택한 post 제목 저장하기

            val postChatIdxGetService = PostChatIdxGetService()
            postChatIdxGetService.setPostChatIdxGetResult(this)
            postChatIdxGetService.getPostChatIdx(chatRoom.chatRoomIdx)
        }




        override fun getPostChatIdxSuccess(code: Int, result: Post) {
            val txtSubject: String = result.title
            Glide.with(context).load(result.image).override(38, 38)
                .into(binding.itemShopImg) // 이미지 가져오기

            binding.itemSubjectTxt.text = txtSubject

            val userGetService = UserGetService()
            userGetService.setUserGetResult(this)
            userGetService.getUser(result.user_id)

            val chatAllGetService = ChatAllGetService()
            chatAllGetService.setChatAllGetResult(this)
            chatAllGetService.getChatAll(result.chatRoom_id)
        }

        override fun getPostChatIdxFailure(code: Int, message: String) {
            Log.d("getPostChatIdxFailure ===============================================", code.toString())
        }

        override fun getUserSuccess(code: Int, result: UserGet) {
            val txtUserID: String = result.name // 상대방 유저 아이디
            binding.itemIdTxt.text = txtUserID
        }

        override fun getUserFailure(code: Int, message: String) {
            Log.d("getUserFailure ===============================================", code.toString())
        }
//
        override fun getChatAllSuccess(code: Int, result: ArrayList<Chat>) {
            if(result.size > 0) {
                var index = result.count() - 1
                val txtContent: String = result[index].content

                var timestampToSdf = Timestamp_to_SDF()
                binding.itemDateTxt.text =
                    timestampToSdf.convert_only_time(result[index].created_at)
                binding.itemChatContentTxt.text = txtContent
            }
            else{
                binding.itemDateTxt.text = ""
                binding.itemChatContentTxt.text = "채팅을 시작해보세요"

            }
        }

        override fun getChatAllFailure(code: Int, message: String) {
            Log.d("getUserFailure ===============================================", code.toString())
        }


    }

    interface OnItemClickListener {
        fun onItemClick(chatRoom: ChatRoomList)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener

    private fun findChatRoomId(result: ArrayList<Post>): Int {
        var i = 0
        while (i < result.size) {
            if (result[i].chatRoom_id == chatRoom_id_list[0]) {
                previous_found_chatRoom_id = result[i].chatRoom_id
                previous_found_index = 0 // chatRoom_id_list에서 찾았던 인덱스값 저장
                found_post_index = i
                isFirst = !isFirst
                return result[i].user_id
            }
            i++
        }
        return 0
    }

    private fun findChatRoomId2(result: ArrayList<Post>): Int {
        var i = 1
        var j = 0
        while (i < result.size) { // result로 받은 array size 만큼 반복
            while (j < chatRoom_id_list.size) { // id들 저장해둔 array size만큼 반복
                if (chatRoom_id_list[j] == result[i].chatRoom_id && result[i].chatRoom_id != previous_found_chatRoom_id) { // id 저장한 값과 반환받은 post 안의 chatRoom_id와 같을 때
                    if (j > previous_found_index) { // 이전에 찾은 값과 다를 때
                        previous_found_index = j
                        previous_found_chatRoom_id = result[i].chatRoom_id
                        found_post_index = i

                        return result[i].user_id
                    }
                }
                j++
                if (j == result.size) {
                    j = 0
                    break
                }
            }
            i++
        }
        return 0
    }
}