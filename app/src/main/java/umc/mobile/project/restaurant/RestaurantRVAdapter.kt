package umc.mobile.project.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.databinding.RestaurantRecyclerviewItemBinding

class RestaurantRVAdapter(private val restaurantData: ArrayList<RestaurantData>): RecyclerView.Adapter<RestaurantRVAdapter.MyViewHolder>() {
    lateinit var context : Context
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val binding: RestaurantRecyclerviewItemBinding = RestaurantRecyclerviewItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        context = viewGroup.context
        return MyViewHolder(binding)
    }
    override fun getItemCount(): Int = restaurantData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(restaurantData[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(restaurantData[position])
            notifyItemChanged(position)
        }
    }

    inner class MyViewHolder(private val binding: RestaurantRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(restaurantData: RestaurantData){
            binding.resTitle.text = restaurantData.title
            binding.resPlace.text = restaurantData.place
            binding.resPhone.text = restaurantData.phone
            binding.resScore.text = restaurantData.score
            Glide.with(itemView).load(restaurantData.image).into(binding.resImg)




        }
    }

    interface OnItemClickListener {
        fun onItemClick( restaurantData: RestaurantData)

    }
    fun setItemClickListener(onItemClickListener:OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

}