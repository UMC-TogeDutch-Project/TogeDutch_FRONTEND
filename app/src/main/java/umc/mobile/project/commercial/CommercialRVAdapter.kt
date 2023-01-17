package umc.mobile.project.commercial

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.CommercialRecyclerviewItemBinding

class CommercialRVAdapter (private val commercialData: ArrayList<CommercialData>): RecyclerView.Adapter<CommercialRVAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val binding: CommercialRecyclerviewItemBinding = CommercialRecyclerviewItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        return MyViewHolder(binding)
    }
    override fun getItemCount(): Int = commercialData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(commercialData[position])
    }

    inner class MyViewHolder(private val binding: CommercialRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(commercialData: CommercialData){
            binding.comDate.text = commercialData.date
            binding.comTitle.text = commercialData.title
            binding.comName.text = commercialData.name

        }
    }



}