package umc.mobile.project.wishlist

import Post
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.databinding.WishlistAdapterBinding
import umc.mobile.project.ram.Geocoder_location
import umc.mobile.project.ram.my_application_1.Timestamp_to_SDF

class WishListRVAdapter (private val wishApplicationList: ArrayList<Post>) : RecyclerView.Adapter<WishListRVAdapter.ViewHolder>() {
    lateinit var context : Context

    // 보여줄 아이템 개수만큼 View를 생성 (RecyclerView가 초기화 될 때 호출)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): WishListRVAdapter.ViewHolder {
        val binding: WishlistAdapterBinding = WishlistAdapterBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )

        context = viewGroup.context

        return ViewHolder(binding)
    }

    // 보여줄 아이템 개수
    override fun getItemCount(): Int = wishApplicationList.size

    // 생성된 View에 보여줄 데이터를 설정
    override fun onBindViewHolder(holder: WishListRVAdapter.ViewHolder, position: Int) {
        holder.bind(wishApplicationList[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(wishApplicationList[position])
            notifyItemChanged(position)
        }
    }

    // ViewHolder 단위 객체로 View의 데이터를 설정
    inner class ViewHolder(val binding: WishlistAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            var selected_random_btn : Int = 0
            var isSelected = false

            val txt_title : String = post.title

            var latLong_to_address : String = Geocoder_location().calculate_location(context, post.latitude, post.longitude)
            var txt_location = latLong_to_address

            val txt_time = post.order_time
//            2022-01-23T03:34:56.000+00:00
            var txt_hour = txt_time.substring(11 until 13)
            var txt_minute = txt_time.substring(14 until 16)
            var txt_time_substring = txt_hour+"시" + txt_minute + "분 주문"

            val txt_recruited : Int = post.recruited_num
            val txt_recruits : Int = post.num_of_recruits

            Glide.with(context).load(post.image).centerCrop().into(binding.listItemPicture)

            binding.orderListTitle.text = txt_title
            binding.orderListLocation.text = txt_location
            binding.orderListTime.text = txt_time_substring
            binding.currentPersonNum.text = txt_recruited.toString() // 현재 사람
            binding.totalPersonNum.text = txt_recruits.toString() // 필요 인원

        }
    }

    // 클릭 리스너 추가
    interface OnItemClickListener {
        fun onItemClick(post: Post)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

}


