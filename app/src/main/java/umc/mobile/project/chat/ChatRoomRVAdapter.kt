package umc.mobile.project.chat

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemChatRoomBinding



class ChatRoomRVAdapter (private val chatRoomList: ArrayList<ChatRoom>) : RecyclerView.Adapter<ChatRoomRVAdapter.ViewHolder>(){

    // 아이템 레이아웃 결합
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemChatRoomBinding = ItemChatRoomBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        return ViewHolder(binding)
    }

    // 아이템 개수
    override fun getItemCount(): Int = chatRoomList.size

    // view에 내용 입력
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chatRoomList[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(chatRoomList[position])
            notifyItemChanged(position)
        }
    }

    // 레이아웃 내 view 연결
    inner class ViewHolder(val binding: ItemChatRoomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatRoom: ChatRoom) {

            val txtSubject : String = chatRoom.subject
            val txtUserID : String = chatRoom.user_id
            val txtContent : String = chatRoom.content
            val txtTime : String = chatRoom.time

            binding.itemDateTxt.text = txtTime
            binding.itemSubjectTxt.text = txtSubject
            binding.itemIdTxt.text = txtUserID
            binding.itemChatContentTxt.text = txtContent
        }
    }

    interface OnItemClickListener {
        fun onItemClick(chatRoom: ChatRoom)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener


}