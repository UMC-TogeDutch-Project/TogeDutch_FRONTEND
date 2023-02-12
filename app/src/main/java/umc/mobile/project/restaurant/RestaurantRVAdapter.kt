package umc.mobile.project.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.RestaurantRecyclerviewItemBinding

class RestaurantRVAdapter(private val restaurantData: ArrayList<umc.mobile.project.restaurant.Auth.PlaceApi.Place>): RecyclerView.Adapter<RestaurantRVAdapter.MyViewHolder>() {
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
        fun bind(restaurantData: umc.mobile.project.restaurant.Auth.PlaceApi.Place){
//            binding.resTitle.text = restaurantData.name
//            binding.resPlace.text = "아직 미완"
//            binding.resPhone.text = restaurantData.phoneNumber
//            binding.resScore.text = "아직 미완"


        }
    }

    interface OnItemClickListener {
        fun onItemClick(restaurantData: umc.mobile.project.restaurant.Auth.PlaceApi.Place)

    }
    fun setItemClickListener(onItemClickListener:OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

}