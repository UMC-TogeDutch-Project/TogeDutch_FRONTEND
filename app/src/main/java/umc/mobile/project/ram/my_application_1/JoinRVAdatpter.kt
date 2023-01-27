package umc.mobile.project.ram.my_application_1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemParticipateBinding


class JoinRVAdatpter(private val joinList: ArrayList<Post>) : RecyclerView.Adapter<JoinRVAdatpter.ViewHolder>(){

    // 아이템 레이아웃 결합
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemParticipateBinding = ItemParticipateBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        return ViewHolder(binding)
    }

    // 아이템 개수
    override fun getItemCount(): Int = joinList.size

    // view에 내용 입력
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(joinList[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(joinList[position])
            notifyItemChanged(position)
        }
    }

    // 레이아웃 내 view 연결
    inner class ViewHolder(val binding: ItemParticipateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {

            val title : String = post.title
//            val item_pic : String = currentApplicatoin.nickname
            val location : String = "동덕여대 앞" // 위도경도 계산,,?
            val time : String = post.order_time

            binding.orderListTitle.text = title
            binding.orderListLocation.text = location
            binding.orderListTime.text = time

        }
    }

    interface OnItemClickListener {
        fun onItemClick(post: Post)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener


}