package umc.mobile.project.restaurant

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.RestaurantFragment
import umc.mobile.project.databinding.RestaurantRecyclerviewItemBinding
import umc.mobile.project.restaurant.Auth.NaverApi.NaverData
import umc.mobile.project.restaurant.Auth.NaverApi.NaverImageSearchAPI
import umc.mobile.project.restaurant.blog.RestaurantPageActivity

import java.util.logging.Handler


class RestaurantRVAdapter(private val naverList: ArrayList<NaverData.NaverSearchData>) : RecyclerView.Adapter<RestaurantRVAdapter.MyViewHolder>() {
    private var imgList = ArrayList<NaverData.NaverImgData>()
    var handler= android.os.Handler()
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

//        holder.itemView.setOnClickListener {
//            itemClickListener.onItemClick(naverList[position] as NaverData.NaverSearchData)
//            notifyItemChanged(position)
//        }
    }

    inner class MyViewHolder(private val binding: RestaurantRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(restaurantData: NaverData.NaverSearchData){
            binding.resTitle.text = restaurantData.title
            binding.resPlace.text = restaurantData.address
            binding.resPhone.text = restaurantData.category
//            binding.resScore.text = restaurantData.description

//            binding.resPlace.text = restaurantData.place
//            binding.resPhone.text = restaurantData.phone
//            binding.resScore.text = restaurantData.score
            itemView.setOnClickListener {
                Intent(context, RestaurantPageActivity::class.java).apply {
                    putExtra("data1", restaurantData.title)
                    putExtra("data2", restaurantData.address)
                    putExtra("data3", restaurantData.category)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }


        }



    }

    interface OnItemClickListener {
        fun onItemClick(restaurantData: NaverData.NaverSearchData)

    }
    fun setItemClickListener(onItemClickListener:OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

}