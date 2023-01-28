package umc.mobile.project.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.WishlistAdapterBinding

class WishListRVAdapter (private val wishApplicationList: ArrayList<WishApplication>) : RecyclerView.Adapter<WishListRVAdapter.ViewHolder>(),
    Filterable {

    // 보여줄 아이템 개수만큼 View를 생성 (RecyclerView가 초기화 될 때 호출)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): WishListRVAdapter.ViewHolder {
        val binding: WishlistAdapterBinding = WishlistAdapterBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )
        return ViewHolder(binding)
    }

    // 보여줄 아이템 개수
    override fun getItemCount(): Int = wishApplicationList.size

    // 생성된 View에 보여줄 데이터를 설정
    override fun onBindViewHolder(holder: WishListRVAdapter.ViewHolder, position: Int) {
        holder.bind(wishApplicationList[position])

    }

    // ViewHolder 단위 객체로 View의 데이터를 설정
    inner class ViewHolder(val binding: WishlistAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(application: WishApplication) {
            binding.orderListTitle.text = application.title
            binding.orderListLocation.text = "동덕여대 인문관 앞"
            binding.orderListTime.text = application.order_time
            binding.currentPersonNum.text = application.recruited_num.toString()
            binding.totalPersonNum.text = application.num_of_recruits.toString()


        }
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

}


