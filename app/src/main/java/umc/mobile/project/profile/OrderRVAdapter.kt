package umc.mobile.project.profile

import Post
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.databinding.OrderListAdapterBinding
import umc.mobile.project.databinding.ReviewCollectionDialogBinding
import umc.mobile.project.ram.Geocoder_location
import umc.mobile.project.ram.my_application_1.post_id_to_detail


class OrderRVAdapter (private val orderList: ArrayList<Post>): RecyclerView.Adapter<OrderRVAdapter.ViewHolder>() {
    private lateinit var viewBinding: ReviewCollectionDialogBinding
    private lateinit var reviewRVAdapter: ReviewRVAdapter
    var reviewList = ArrayList<ReviewData>()
    lateinit var context : Context

    // 보여줄 아이템 개수만큼 View를 생성 (RecyclerView가 초기화 될 때 호출)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): OrderRVAdapter.ViewHolder {
        val binding: OrderListAdapterBinding = OrderListAdapterBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )
        context = viewGroup.context

        viewBinding = ReviewCollectionDialogBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )

        initReview()

        return ViewHolder(binding)
    }

    // 보여줄 아이템 개수
    override fun getItemCount(): Int = orderList.size

    // 생성된 View에 보여줄 데이터를 설정
    override fun onBindViewHolder(holder: OrderRVAdapter.ViewHolder, position: Int) {
        holder.bind(orderList[position])
        holder.itemView.setOnClickListener {
            post_id_to_detail = orderList[position].post_id
            itemClickListener.onItemClick(orderList[position])
            notifyItemChanged(position)
        }
    }

    // ViewHolder 단위 객체로 View의 데이터를 설정
    inner class ViewHolder(val binding: OrderListAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
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

            binding.orderListTitle.text = txt_title // 제목
            binding.orderListLocation.text = txt_location// 위치
            binding.orderListTime.text = txt_time_substring

            // 후기 점수


            binding.listItemScore.setOnClickListener {
                val dialog = ReviewPopupDialog(context)
                dialog.start()

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

    private fun initReview() {
        reviewList.apply {
            add(ReviewData("친절하고 약속을 잘 지켜서 좋았어요!"))
            add(ReviewData("좋았습니당"))
            add(ReviewData("굿굿"))
            add(ReviewData("친절해요"))
        }
        reviewRVAdapter = ReviewRVAdapter(reviewList)

        viewBinding.reviewList.adapter = reviewRVAdapter
    }
}