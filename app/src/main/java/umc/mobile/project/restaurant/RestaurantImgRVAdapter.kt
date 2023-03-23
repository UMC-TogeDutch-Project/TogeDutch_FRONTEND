package umc.mobile.project.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.databinding.RestaurantRecyclerviewItem2Binding
import umc.mobile.project.restaurant.Auth.NaverApi.NaverData


class RestaurantImgRVAdapter(private var naverImgList: ArrayList<NaverData.NaverImgData>) : RecyclerView.Adapter<RestaurantImgRVAdapter.MyViewHolder2>() {

    lateinit var context : Context
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder2 {
        val binding: RestaurantRecyclerviewItem2Binding = RestaurantRecyclerviewItem2Binding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        context = viewGroup.context
        return MyViewHolder2(binding)
    }
    override fun getItemCount(): Int = naverImgList.size

    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {

        holder.bind(naverImgList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(naverImgList[position] )
            notifyItemChanged(position)
        }
    }

    inner class MyViewHolder2(private val binding: RestaurantRecyclerviewItem2Binding): RecyclerView.ViewHolder(binding.root){
        fun bind(restaurantData: NaverData.NaverImgData){

            Glide.with(context).load(restaurantData.thumbnail).centerCrop().into(binding.resImg)

//            binding.resPlace.text = restaurantData.place
//            binding.resPhone.text = restaurantData.phone
//            binding.resScore.text = restaurantData.score


        }



    }

    interface OnItemClickListener {
        fun onItemClick(restaurantData: NaverData.NaverImgData)

    }
    fun setItemClickListener(onItemClickListener:OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

}