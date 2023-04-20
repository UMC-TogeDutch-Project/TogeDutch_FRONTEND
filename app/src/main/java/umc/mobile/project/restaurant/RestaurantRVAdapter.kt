package umc.mobile.project.restaurant

import NaverBlogAPI
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import umc.mobile.project.RestaurantFragment
import umc.mobile.project.databinding.RestaurantRecyclerviewItemBinding
import umc.mobile.project.restaurant.Auth.NaverApi.NaverData
import umc.mobile.project.restaurant.blog.BlogData
import umc.mobile.project.restaurant.blog.BlogRVAdapter

import umc.mobile.project.restaurant.blog.RestaurantPageActivity


class RestaurantRVAdapter(private val naverList: ArrayList<NaverData.NaverSearchData>, private val naverList2: ArrayList<NaverData.NaverImgData>) : RecyclerView.Adapter<RestaurantRVAdapter.MyViewHolder>() {

    lateinit var context : Context
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
                val binding: RestaurantRecyclerviewItemBinding = RestaurantRecyclerviewItemBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup, false)
        context = viewGroup.context


        return MyViewHolder(binding)
    }
    override fun getItemCount(): Int = naverList.size + naverList2.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(naverList[position], naverList2[position])


    }

    inner class MyViewHolder(private val binding: RestaurantRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(restaurantData: NaverData.NaverSearchData, restaurantData2: NaverData.NaverImgData){
            binding.resTitle.text = restaurantData.title
            binding.resPlace.text = restaurantData.address
            binding.resPhone.text = restaurantData.category
            Glide.with(context).load(restaurantData2.thumbnail).centerCrop().apply(RequestOptions.bitmapTransform(
                RoundedCorners(13)
            )).into(binding.resImg)

            itemView.setOnClickListener {

                Intent(context, RestaurantPageActivity::class.java).apply {
                    putExtra("data1", restaurantData.title)
                    putExtra("data2", restaurantData.address)
                    putExtra("data3", restaurantData.category)
                    putExtra("data4", restaurantData2.thumbnail)
                     when(position){
                         0 -> putExtra("position", 0 )
                         1 -> putExtra("position", 1 )
                         2 -> putExtra("position", 2 )
                         3 -> putExtra("position", 3 )
                         4 -> putExtra("position", 4 )

                     }
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