package umc.mobile.project.mypage.profile

import Post
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.DataRecentRVAdapter
import umc.mobile.project.databinding.OrderListAdapterBinding
import umc.mobile.project.databinding.ReviewCollectionDialogBinding
import umc.mobile.project.mypage.profile.emotionStatus.EmotionStatusGet
import umc.mobile.project.mypage.profile.emotionStatus.EmotionStatusGetResult
import umc.mobile.project.mypage.profile.emotionStatus.EmotionStatusGetService
import umc.mobile.project.ram.Geocoder_location
import umc.mobile.project.ram.my_application_1.post_id_to_detail
import umc.mobile.project.ram.my_application_1.user_id_logined


class OrderRVAdapter (private val orderList: ArrayList<Post>, private val score_list: ArrayList<EmotionStatusGet>): RecyclerView.Adapter<OrderRVAdapter.ViewHolder>(){
    val TAG: String = "로그"

    private lateinit var viewBinding: ReviewCollectionDialogBinding
    private lateinit var reviewRVAdapter: ReviewRVAdapter

    lateinit var context : Context

    var postId : Int = -1
    lateinit var scoreTextView : TextView

    lateinit var binding: OrderListAdapterBinding

//    private val reviewScore = arrayListOf<EmotionStatusGet>()


    // 보여줄 아이템 개수만큼 View를 생성 (RecyclerView가 초기화 될 때 호출)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
       binding = OrderListAdapterBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )
        context = viewGroup.context

//        getEmotionStatus()

        viewBinding = ReviewCollectionDialogBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )

        return ViewHolder(binding)
    }

    // 보여줄 아이템 개수
    override fun getItemCount(): Int = orderList.size

    // 생성된 View에 보여줄 데이터를 설정
    override fun onBindViewHolder(holder: OrderRVAdapter.ViewHolder, position: Int) {
        if(holder is ViewHolder) {
            holder.bind(orderList[position])
            post_id_to_detail = orderList[position].post_id

            holder.itemView.setOnClickListener {
                itemClickListener.onItemClick(orderList[position])
                notifyItemChanged(position)
            }
        }
    }

    // ViewHolder 단위 객체로 View의 데이터를 설정
    inner class ViewHolder(val binding: OrderListAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            Log.d("score_list size: ", score_list.size.toString())
            score_list.forEach {
                if(post.post_id == it.post_id) run {
                    binding.score.text = it.avg.toString()
                }
            }
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

            postId = post.post_id

            binding.listItemScore.setOnClickListener {
                Log.d(TAG, "화면 연결")
                val dialog = ReviewPopupDialog(context, postId)
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

    override fun getItemViewType(position: Int): Int {
        return position
    }
}