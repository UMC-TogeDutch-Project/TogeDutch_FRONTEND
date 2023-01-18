package umc.mobile.project.announcement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.AnnounceRecyclerviewItemBinding

class AnnounceRVAdapter(private val announceData: ArrayList<AnnounceData>): RecyclerView.Adapter<AnnounceRVAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val binding: AnnounceRecyclerviewItemBinding = AnnounceRecyclerviewItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        return MyViewHolder(binding)
    }
    override fun getItemCount(): Int = announceData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(announceData[position])
    }

        inner class MyViewHolder(private val binding: AnnounceRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(announceData: AnnounceData){
                binding.notTitle.text = announceData.title
                binding.notPlace.text = announceData.place
                binding.notTime.text = announceData.time
                binding.notPerson.text = announceData.person
            }
        }



    }



