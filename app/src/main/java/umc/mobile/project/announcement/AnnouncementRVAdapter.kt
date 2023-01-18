package umc.mobile.project.announcement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.NoticeRecyclerviewItemBinding

class AnnouncementRVAdapter(private val announcementData: ArrayList<AnnouncementData>): RecyclerView.Adapter<AnnouncementRVAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val binding: NoticeRecyclerviewItemBinding = NoticeRecyclerviewItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        return MyViewHolder(binding)
    }
    override fun getItemCount(): Int = announcementData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(announcementData[position])
    }

        inner class MyViewHolder(private val binding: NoticeRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(announcementData: AnnouncementData){
                binding.notTitle.text = announcementData.title
                binding.notPlace.text = announcementData.place
                binding.notTime.text = announcementData.time
                binding.notPerson.text = announcementData.person
            }
        }



    }



