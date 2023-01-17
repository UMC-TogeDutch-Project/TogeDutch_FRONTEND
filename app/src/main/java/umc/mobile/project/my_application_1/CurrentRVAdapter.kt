package umc.mobile.project.my_application_1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.R
import umc.mobile.project.chat.ChatRoom
import umc.mobile.project.databinding.ItemApplyCurrentBinding

class CurrentRVAdapter(private val currentList: ArrayList<CurrentData>) : RecyclerView.Adapter<CurrentRVAdapter.ViewHolder>(){

    // 아이템 레이아웃 결합
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemApplyCurrentBinding = ItemApplyCurrentBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        return ViewHolder(binding)
    }

    // 아이템 개수
    override fun getItemCount(): Int = currentList.size

    // view에 내용 입력
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(currentList[position])
            notifyItemChanged(position)
        }
    }

    // 레이아웃 내 view 연결
    inner class ViewHolder(val binding: ItemApplyCurrentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentData: CurrentData) {

            val txtSubject : String = currentData.subject
            val txtUserID : String = currentData.nickname
            val txtAlaram : String = currentData.alarm_txt
            val txtDate : String = currentData.time

            binding.itemDateTxt.text = txtDate
            binding.itemSubjectTxt.text = txtSubject
            binding.itemAlarmIdTxt.text = txtUserID
            binding.itemAlarmTxt.text = txtAlaram

            binding.itemAcceptBtn.setOnClickListener {
                binding.itemAlarmTxt.text = "의 요청이 수락되었습니다."
            }

            binding.itemRefuseBtn.setOnClickListener {
                binding.itemAlarmTxt.text = "의 요청이 거절되었습니다."
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(currentData: CurrentData)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener


}