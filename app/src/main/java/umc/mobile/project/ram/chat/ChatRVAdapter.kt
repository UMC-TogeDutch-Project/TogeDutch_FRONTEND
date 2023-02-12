package umc.mobile.project.ram.chat

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text
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
    lateinit var glide_context : Context

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
        glide_context = parent.context
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
            4 -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_meettime, parent, false
                )
                MeetTime_ViewHolder(view)
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
            4->{
                (holder as MeetTime_ViewHolder).bind(chatList[position])
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
        private val image_time : TextView = view.findViewById(R.id.your_image_date_txt)
        private val picture : ImageView = view.findViewById(R.id.your_image_iv)

        fun bind(chat: Chat) {
            name.text = chat.writer

            var length = chat.content.length
            var substring_str = ""
            if(length >= 4) {
                substring_str = chat.content.substring(length - 4, length)
            }

            var timestampToSdf = Timestamp_to_SDF()

            if(substring_str.equals(".jpg")){ // content로 들어온 게 이미지일 때
                content.visibility = View.GONE
                time.visibility = View.GONE
                image_time.visibility = View.VISIBLE
                picture.visibility = View.VISIBLE
                Glide.with(glide_context).load(chat.content).into(picture)
                image_time.text = timestampToSdf.convert_only_time(chat.created_at)
            }else{
                content.text = chat.content
                time.text = timestampToSdf.convert_only_time(chat.created_at)
            }

        }

    }

    inner class MeetTime_ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val meet_time: TextView = view.findViewById(R.id.item_meet_time_text)

        fun bind(chat: Chat) {

            val txt_time = chat.content
//            2022-01-23T03:34:56.000+00:00

            var txt_year = txt_time.substring(0 until 4)
            var txt_month = txt_time.substring(5 until 7)
            var txt_day = txt_time.substring(8 until 10)

            var txt_hour = txt_time.substring(11 until 13)
            var txt_minute = txt_time.substring(14 until 16)
            var txt_timestamp_substring = txt_year + "년 " + txt_month + "월 " + txt_day + "일 " + txt_hour+"시 " + txt_minute + "분"
            meet_time.text = txt_timestamp_substring

        }

    }

    inner class RightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val content: AppCompatButton = view.findViewById(R.id.my_chat_iv)
        private val time : TextView = view.findViewById(R.id.my_chat_date_txt)
        private val image_time : TextView = view.findViewById(R.id.my_image_date_txt)
        private val picture : ImageView = view.findViewById(R.id.my_image_iv)
        fun bind(chat: Chat) {
            var timestampToSdf = Timestamp_to_SDF()

            var length = chat.content.length
            var substring_str = ""
            if(length >= 4) {
                substring_str = chat.content.substring(length - 4, length)
            }

            if(substring_str.equals(".jpg")){ // content로 들어온 게 이미지일 때
                content.visibility = View.GONE
                time.visibility = View.GONE
                image_time.visibility = View.VISIBLE
                picture.visibility = View.VISIBLE
                Glide.with(glide_context).load(chat.content).into(picture)
                image_time.text = timestampToSdf.convert_only_time(chat.created_at)
            }else {
                content.text = chat.content
                time.text = timestampToSdf.convert_only_time(chat.created_at)
            }
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


