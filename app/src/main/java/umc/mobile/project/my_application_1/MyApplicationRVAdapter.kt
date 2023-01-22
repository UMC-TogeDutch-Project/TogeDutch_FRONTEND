package umc.mobile.project.my_application_1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemMyApplicationBinding

class MyApplicationRVAdapter (private val applicationList: ArrayList<Application>) : RecyclerView.Adapter<MyApplicationRVAdapter.ViewHolder>(){

    // 아이템 레이아웃 결합
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMyApplicationBinding = ItemMyApplicationBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        return ViewHolder(binding)
    }

    // 아이템 개수
    override fun getItemCount(): Int = applicationList.size

    // view에 내용 입력
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(applicationList[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(applicationList[position])
            notifyItemChanged(position)
        }
    }

    // 레이아웃 내 view 연결
    inner class ViewHolder(val binding: ItemMyApplicationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(application: Application) {

            val txt_title : String = application.title
            val txt_location : String = "종로" // 아마 위도경도 계산하는 듯,,,,?
            val txt_time : String = application.order_time
            val txt_recruited : Int = application.recruited_num
            val txt_recruits : Int = application.num_of_recruits

            binding.orderListTitle.text = txt_title // 제목
            binding.orderListLocation.text = txt_location// 위치
            binding.orderListTime.text = txt_time // 주문시간
            binding.numRecruited.text = txt_recruited.toString() // 현재 사람
            binding.numRecruits.text = txt_recruits.toString() // 필요 인원

            // 수정 버튼
            binding.modifyBtn.setOnClickListener {

            }

            // 삭제 버튼
            binding.deleteBtn.setOnClickListener {

            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(application: Application)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener


}