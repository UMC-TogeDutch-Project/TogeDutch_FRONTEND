package umc.mobile.project.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.NoticeRecyclerviewItemBinding

class NoticeRVAdapter(private val noticeData: ArrayList<NoticeData>): RecyclerView.Adapter<NoticeRVAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val binding: NoticeRecyclerviewItemBinding = NoticeRecyclerviewItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        return MyViewHolder(binding)
    }
    override fun getItemCount(): Int = noticeData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(noticeData[position])
    }

        inner class MyViewHolder(private val binding: NoticeRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(noticeData: NoticeData){
                binding.notTitle.text = noticeData.title
                binding.notPlace.text = noticeData.place
                binding.notTime.text = noticeData.time
                binding.notPerson.text = noticeData.person
            }
        }



    }



