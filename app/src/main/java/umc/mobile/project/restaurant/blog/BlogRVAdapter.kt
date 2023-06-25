package umc.mobile.project.restaurant.blog

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemRestaurantBlogBinding

class BlogRVAdapter (private val blogData:  ArrayList<BlogData>): RecyclerView.Adapter<BlogRVAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemRestaurantBlogBinding = ItemRestaurantBlogBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        return MyViewHolder(binding)
    }
    override fun getItemCount(): Int = blogData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = blogData[position]
        holder.bind(blogData[position])
        holder.itemView.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
            context.startActivity(intent)
        }
    }

    inner class MyViewHolder(private val binding: ItemRestaurantBlogBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(blogData: BlogData){
            binding.resBlogTitle.text = blogData.title
            binding.resBlogDate.text = blogData.date
            binding.resBlogId.text = blogData.name


        }
    }


}