package umc.mobile.project.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.RestaurantRecyclerviewItemBinding
import umc.mobile.project.restaurant.Auth.NaverApi.NaverSearchData

class RestaurantRVAdapter: RecyclerView.Adapter<RestaurantRVAdapter.MyViewHolder>() {
    var naverList = ArrayList<NaverSearchData>()
    lateinit var context : Context
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val binding: RestaurantRecyclerviewItemBinding = RestaurantRecyclerviewItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        context = viewGroup.context
        return MyViewHolder(binding)
    }
    override fun getItemCount(): Int = naverList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(naverList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(naverList[position])
            notifyItemChanged(position)
        }
    }

    inner class MyViewHolder(private val binding: RestaurantRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(restaurantData: NaverSearchData){
            binding.resTitle.text = restaurantData.title
            binding.resPlace.text = restaurantData.address
            binding.resPhone.text = restaurantData.category
            binding.resScore.text = restaurantData.description
//            binding.resPlace.text = restaurantData.place
//            binding.resPhone.text = restaurantData.phone
//            binding.resScore.text = restaurantData.score
//            Glide.with(itemView).load(restaurantData.image).into(binding.resImg)





        }

    }

    interface OnItemClickListener {
        fun onItemClick(restaurantData: NaverSearchData)

    }
    fun setItemClickListener(onItemClickListener:OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

}