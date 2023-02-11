package umc.mobile.project.ram.my_application_1

import Post
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.databinding.ItemParticipateBinding
import umc.mobile.project.ram.Geocoder_location


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
            user_id_var = joinList[position].user_id
            post_id_to_detail = joinList[position].post_id
            Log.d("post_id_to_detail 값: ", post_id_to_detail.toString())
            Log.d("post_id 값: ", joinList[position].post_id.toString())
            itemClickListener.onItemClick(joinList[position])
            notifyItemChanged(position)
        }
    }

    // 레이아웃 내 view 연결
    inner class ViewHolder(val binding: ItemParticipateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {

            val txt_title : String = post.title

            var latLong_to_address : String = Geocoder_location().calculate_location(context, post.latitude, post.longitude)
            var txt_location = latLong_to_address

            val txt_time = post.order_time
//            2022-01-23T03:34:56.000+00:00
            var txt_hour = txt_time.substring(11 until 13)
            var txt_minute = txt_time.substring(14 until 16)
            var txt_time_substring = txt_hour+"시" + txt_minute + "분 주문"


            Glide.with(context).load(post.image).centerCrop().into(binding.listItemPicture)
            binding.orderListTitle.text = txt_title
            binding.orderListLocation.text = txt_location
            binding.orderListTime.text = txt_time_substring // 주문시간

            binding.listItemReview.setOnClickListener {
                post_id_to_detail = post.post_id
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