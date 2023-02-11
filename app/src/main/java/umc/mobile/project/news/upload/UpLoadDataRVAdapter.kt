package umc.mobile.project.news.upload

import android.content.Context
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.DataRecentRVAdapter
import umc.mobile.project.databinding.ItemNewsUploadDataBinding
import umc.mobile.project.ram.Geocoder_location
import umc.mobile.project.ram.my_application_1.post_id_to_detail
import umc.mobile.project.ram.my_application_1.user_id_var

class UpLoadDataRVAdapter(private val UpLoadDataList: ArrayList<UpLoadData>) : RecyclerView.Adapter<UpLoadDataRVAdapter.DataViewHolder>() {

    lateinit var context : Context
    private val checkboxStatus = SparseBooleanArray()

    //ViewHolder객체
    inner class DataViewHolder(private val viewBinding: ItemNewsUploadDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(upLoadData: UpLoadData) {

            var latLong_to_address : String = Geocoder_location().calculate_location(context, upLoadData.latitude, upLoadData.longitude)
            var txt_location = latLong_to_address

            viewBinding.tvDate.text = upLoadData.created_at.toString()
            Glide.with(context).load(upLoadData.image).centerCrop().into(viewBinding.ivMainImage)
//            viewBinding.ivMainImage.setImageResource(upLoadData.ivMainImage!!)
            viewBinding.tvTitle.text = upLoadData.title
            viewBinding.tvUserId.text = upLoadData.user_id.toString()
            viewBinding.tvItemWhere.text = txt_location

            val txt_time = upLoadData.order_time
//            2022-01-23T03:34:56.000+00:00
            var txt_hour = txt_time.substring(11 until 13)
            var txt_minute = txt_time.substring(14 until 16)
            var txt_time_substring = txt_hour+"시" + txt_minute + "분 주문"
            viewBinding.tvItemTime.text = txt_time_substring // 주문한 시간


        }
    }

    //ViewHolder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding =
            ItemNewsUploadDataBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        context = viewGroup.context
        return DataViewHolder(viewBinding)
    }

    //ViewHolder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        holder.bind(UpLoadDataList[position])
        post_id_to_detail = UpLoadDataList[position].post_id
        holder.itemView.setOnClickListener {
            user_id_var = UpLoadDataList[position].user_id
            Log.d("user_id : ===========" , user_id_var.toString())
            itemClickListener.onItemClick(UpLoadDataList[position])
            notifyItemChanged(position)
        }


    }

    //표현할 아이템의 총 갯수
    override fun getItemCount(): Int = UpLoadDataList.size

    interface OnItemClickListener {
        fun onItemClick(upLoadData: UpLoadData)

    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener
}
