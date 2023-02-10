package umc.mobile.project.ram.chat

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.R
import umc.mobile.project.databinding.ItemApplyCurrentBinding
import umc.mobile.project.databinding.ItemMyChatBinding
import umc.mobile.project.databinding.ItemYourChatBinding
import umc.mobile.project.ram.Auth.Application.GetUser.UserGet
import umc.mobile.project.ram.Auth.Application.GetUser.UserGetResult
import umc.mobile.project.ram.Auth.Application.GetUser.UserGetService
import umc.mobile.project.ram.my_application_1.Timestamp_to_SDF
import umc.mobile.project.ram.my_application_1.current_application.CurrentRVAdapter
import java.text.SimpleDateFormat

class ChatRVAdapter(
    val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var chatList = mutableListOf<Chat>()

    interface MyItemClickListener {
        fun clickButton1(chat: Chat)
        fun clickButton2(chat: Chat)
//        fun setQuizAnswer(roomId:Int, userId:Int, answer:Int)
//        fun getQuizAnswer(roomId: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    //처음에 화면에 보일 아이템뷰 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View?

        return when (viewType) {
            1 -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_your_chat, parent, false
                )
                LeftViewHolder(view)
            }
            2 -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_my_chat, parent, false
                )
                RightViewHolder(view)
            }
            3 -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_time_chat, parent, false
                )
                CenterViewHolder(view)
            }
            else -> {
                throw RuntimeException("Error")
            }
        }
    }

    //뷰홀더에 데이터를 바인딩할때마다 호출되는 함수
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (chatList[position].viewType) {
            1 -> {
                (holder as LeftViewHolder).bind(chatList[position])
                holder.setIsRecyclable(false)
            }
            2 -> {
                (holder as RightViewHolder).bind(chatList[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                (holder as CenterViewHolder).bind(chatList[position])
                holder.setIsRecyclable(false)
            }

        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    fun addItem(chat: Chat) {
        chatList.add(chat)
    }

    //xml을 여러개 사용하려면 오버라이딩 해줘야 함
    override fun getItemViewType(position: Int): Int {
        return chatList[position].viewType
    }

    inner class LeftViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val content: AppCompatButton = view.findViewById(R.id.your_chat_iv)
        private val name : TextView = view.findViewById(R.id.your_name_txt)
        private val time : TextView = view.findViewById(R.id.your_chat_date_txt)

        fun bind(chat: Chat) {
            name.text = chat.writer
            content.text = chat.content

            var timestampToSdf = Timestamp_to_SDF()
            time.text = timestampToSdf.convert_only_time(chat.created_at)
        }
    }

    inner class RightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val content: AppCompatButton = view.findViewById(R.id.my_chat_iv)
        private val time : TextView = view.findViewById(R.id.my_chat_date_txt)
        fun bind(chat: Chat) {
            content.text = chat.content

            var timestampToSdf = Timestamp_to_SDF()
            time.text = timestampToSdf.convert_only_time(chat.created_at)
        }
    }

    /// 시간, 나갔습니다, 들어왔습니다 등등
    inner class CenterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val time: TextView = view.findViewById(R.id.item_time_date_txt)

        fun bind(chat: Chat) {
            time.text = chat.content
        }
    }
}


