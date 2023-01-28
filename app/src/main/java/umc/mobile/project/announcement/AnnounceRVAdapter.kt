package umc.mobile.project.announcement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.HomeData
import umc.mobile.project.databinding.ItemDataBinding

class AnnounceRVAdapter(private val announceData: ArrayList<HomeData>): RecyclerView.Adapter<AnnounceRVAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemDataBinding = ItemDataBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        return MyViewHolder(binding)
    }
    override fun getItemCount(): Int = announceData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(announceData[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(announceData[position])
            notifyItemChanged(position)
        }
    }

        inner class MyViewHolder(private val binding: ItemDataBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(announceData: HomeData){
                binding.tvItemTitle.text = announceData.title //제목
                binding.tvItemWhere.text = "주소" // 주소
                binding.tvItemTime.text = announceData.order_time // 주문한 시간
                binding.annApp.text = announceData.recruited_num.toString() // 신청인원
                binding.annRecruit.text = announceData.num_of_recruits.toString() //총 인원
            }
        }

    interface OnItemClickListener {
        fun onItemClick(announceData: HomeData)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

    }



