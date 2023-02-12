package umc.mobile.project.commercial

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.commercial.Auth.CommercialGet.CommercialGet
import umc.mobile.project.databinding.CommercialRecyclerviewItemBinding
import umc.mobile.project.ram.my_application_1.Timestamp_to_SDF
import umc.mobile.project.ram.my_application_1.post_id_to_detail
import umc.mobile.project.ram.my_application_1.user_id_var

class CommercialRVAdapter(private val commercialData: ArrayList<CommercialGet>): RecyclerView.Adapter<CommercialRVAdapter.MyViewHolder>(){
    lateinit var context : Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val binding: CommercialRecyclerviewItemBinding = CommercialRecyclerviewItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        context = viewGroup.context
        return MyViewHolder(binding)
    }
    override fun getItemCount(): Int = commercialData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(commercialData[position])

        holder.itemView.setOnClickListener {
            ad_id = commercialData[position].adIdx
            itemClickListener.onItemClick(commercialData[position])
            notifyItemChanged(position)
        }
    }

    inner class MyViewHolder(private val binding: CommercialRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(commercialData: CommercialGet){
            Glide.with(context).load(commercialData.image).centerCrop().into(binding.comImg)
            val timestampToSdf = Timestamp_to_SDF()
            var txtDate : String = timestampToSdf.convert(commercialData.createdAt) // 시간 가져오기
            binding.comDate.text = txtDate
            binding.comTitle.text = commercialData.store
            binding.comName.text = commercialData.userIdx.toString()

        }
    }

    interface OnItemClickListener {
        fun onItemClick(commercialData: CommercialGet)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener


}