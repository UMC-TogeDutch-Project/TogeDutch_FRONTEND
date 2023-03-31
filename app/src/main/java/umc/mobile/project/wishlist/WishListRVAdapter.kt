package umc.mobile.project.wishlist

import Post
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.R

import umc.mobile.project.databinding.WishlistAdapterBinding
import umc.mobile.project.ram.Geocoder_location
import umc.mobile.project.ram.my_application_1.Timestamp_to_SDF
import umc.mobile.project.ram.my_application_1.post_id_to_detail
import umc.mobile.project.ram.my_application_1.user_id_logined
import umc.mobile.project.ram.my_application_1.user_id_var
import umc.mobile.project.wishlist.LikeDelete.LikeDeleteResult
import umc.mobile.project.wishlist.LikeDelete.LikeDeleteService

import java.util.*
import kotlin.collections.ArrayList

class WishListRVAdapter (private val wishApplicationList: ArrayList<Post>) : RecyclerView.Adapter<WishListRVAdapter.ViewHolder>(),
    Filterable {
    lateinit var context : Context

    var delete_position : Int = 0

    // 보여줄 아이템 개수만큼 View를 생성 (RecyclerView가 초기화 될 때 호출)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): WishListRVAdapter.ViewHolder {
        val binding: WishlistAdapterBinding = WishlistAdapterBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )

        context = viewGroup.context

        return ViewHolder(binding)
    }

    // 보여줄 아이템 개수
    override fun getItemCount(): Int = wishApplicationList.size

    // 생성된 View에 보여줄 데이터를 설정
    override fun onBindViewHolder(holder: WishListRVAdapter.ViewHolder, position: Int) {
        holder.bind(wishApplicationList[position])
        holder.itemView.setOnClickListener {
            post_id_to_detail = wishApplicationList[position].post_id
            itemClickListener.onItemClick(wishApplicationList[position])
            notifyItemChanged(position)
        }
    }

    // ViewHolder 단위 객체로 View의 데이터를 설정
    inner class ViewHolder(val binding: WishlistAdapterBinding) : RecyclerView.ViewHolder(binding.root), LikeDeleteResult {
        fun bind(post: Post) {
            Log.d("post_id: ", post.post_id.toString())

            var selected_random_btn : Int = 0
            var isSelected = false

            val txt_title : String = post.title

            var latLong_to_address : String = Geocoder_location().calculate_location(context, post.latitude, post.longitude)
            var txt_location = latLong_to_address

            val txt_time = post.order_time
//            2022-01-23T03:34:56.000+00:00
            var txt_hour = txt_time.substring(11 until 13)
            var txt_minute = txt_time.substring(14 until 16)
            var txt_time_substring = txt_hour+"시" + txt_minute + "분 주문"

            val txt_recruited : Int = post.recruited_num
            val txt_recruits : Int = post.num_of_recruits

            Glide.with(context).load(post.image).centerCrop().into(binding.listItemPicture)

            binding.orderListTitle.text = txt_title
            binding.orderListLocation.text = txt_location
            binding.orderListTime.text = txt_time_substring
            binding.currentPersonNum.text = txt_recruited.toString() // 현재 사람
            binding.totalPersonNum.text = txt_recruits.toString() // 필요 인원
            binding.appCompatLike.setBackgroundResource(R.drawable.main_item_heart_icon_fill)

            binding.appCompatLike.setOnClickListener{
                Log.d("user_id_logined: ", user_id_logined.toString())
                Log.d("user_id_var: ", user_id_var.toString())

                delete_position = absoluteAdapterPosition
                val likeDeleteService = LikeDeleteService()
                likeDeleteService.setDeletePostResult(this)
                likeDeleteService.deleteLike(user_id_logined, post.post_id)
            }
        }

        override fun getLikeDeleteSuccess(result : Int) {
            binding.appCompatLike.setBackgroundResource(R.drawable.main_item_heart_icon)
            removePost(delete_position)
            Toast.makeText(context, "관심 공고 삭제 성공.", Toast.LENGTH_SHORT).show()
            Log.d("result값: ", result.toString())
        }


        override fun getLikeDeleteFailure(result : Int) {
            Toast.makeText(context, "관심 공고 삭제 실패.", Toast.LENGTH_SHORT).show()
            Log.d("result값: ", result.toString())
        }
    }

    fun removePost(position: Int){
        if(position >= 0){
            wishApplicationList.removeAt(position)

            notifyDataSetChanged()
        }
    }

    // 검색
    val mDataListAll = ArrayList<Post>(wishApplicationList)
    var mAccounts:MutableList<Post> = wishApplicationList as MutableList<Post>
    override fun getFilter(): Filter {
        return exampleFilter
    }
    private val exampleFilter: Filter = object : Filter() {
        // Automatic on background thread
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<Post> = java.util.ArrayList<Post>()
            if(constraint!!.isEmpty()){
                filteredList.addAll(mDataListAll)
            }
            else{
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim{ it <= ' '}
                for(item in mDataListAll){
                    //filter 대상 setting
                    if(item.title.lowercase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(item)
                    }

                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        // Automatic on UI thread
        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            mAccounts.clear()
            mAccounts.addAll(results?.values as Collection<Post>)
            notifyDataSetChanged()
        }

    }

    // 클릭 리스너 추가
    interface OnItemClickListener {
        fun onItemClick(post: Post)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

}


