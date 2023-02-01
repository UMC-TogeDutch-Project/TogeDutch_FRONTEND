package umc.mobile.project

import android.content.Context
import Post
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemDataBinding
import umc.mobile.project.ram.Geocoder_location
import umc.mobile.project.ram.my_application_1.post_id_to_detail
import umc.mobile.project.ram.my_application_1.user_id_var


class DataRecentRVAdapter(private val homeDataList: ArrayList<Post>) : RecyclerView.Adapter<DataRecentRVAdapter.RecentViewHolder>() {

    lateinit var context : Context
    private val checkboxStatus = SparseBooleanArray()

    //ViewHolder 객체
    inner class RecentViewHolder(val viewBinding: ItemDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(homeData: Post) {
            viewBinding.tvItemTitle.text = homeData.title //제목

            var latLong_to_address : String = Geocoder_location().calculate_location(context, homeData.latitude, homeData.longitude)
            var txt_location = latLong_to_address
            viewBinding.tvItemWhere.text = txt_location // 주소

            val txt_time = homeData.order_time
//            2022-01-23T03:34:56.000+00:00
            var txt_hour = txt_time.substring(11 until 13)
            var txt_minute = txt_time.substring(14 until 16)
            var txt_time_substring = txt_hour+"시" + txt_minute + "분 주문"
            viewBinding.tvItemTime.text = txt_time_substring // 주문한 시간

            viewBinding.annApp.text = homeData.recruited_num.toString() // 신청인원
            viewBinding.annRecruit.text = homeData.num_of_recruits.toString() //총 인원

        }

    }

    //ViewHolder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DataRecentRVAdapter.RecentViewHolder {

        val viewBinding: ItemDataBinding = ItemDataBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        context = viewGroup.context
        return RecentViewHolder(viewBinding)

    }

    //ViewHolder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: DataRecentRVAdapter.RecentViewHolder, position: Int) {

        holder.bind(homeDataList[position])
        post_id_to_detail = homeDataList[position].post_id
        holder.itemView.setOnClickListener {
            user_id_var = homeDataList[position].user_id
            Log.d("user_id : ===========" , user_id_var.toString())
            itemClickListener.onItemClick(homeDataList[position])
            notifyItemChanged(position)
        }


    }

    //표현할 아이템의 총 갯수
    override fun getItemCount(): Int = homeDataList.size


    //2
    interface OnItemClickListener {
        fun onItemClick(homeData: Post)

    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener


}
