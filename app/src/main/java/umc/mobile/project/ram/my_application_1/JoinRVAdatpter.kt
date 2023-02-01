package umc.mobile.project.ram.my_application_1

import Post
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemParticipateBinding


class JoinRVAdatpter(private val joinList: ArrayList<Post>) : RecyclerView.Adapter<JoinRVAdatpter.ViewHolder>(){

    lateinit var context : Context
    // 아이템 레이아웃 결합
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemParticipateBinding = ItemParticipateBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        context = viewGroup.context
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
//            val time : String = post.order_time
//            var txt_time : String = "4시 50분"
//            var txt_time = Timestamp_to_SDF().convert(post.order_time)

            binding.orderListTitle.text = title
            binding.orderListLocation.text = location
            binding.orderListTime.text = "주문" // 주문시간

            binding.listItemReview.setOnClickListener {
                val dlg = ReviewWritePopupDialog(context)
                dlg.start()
            }

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