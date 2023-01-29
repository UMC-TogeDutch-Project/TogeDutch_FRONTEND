package umc.mobile.project.ram.my_application_1

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemMyPostBinding
import umc.mobile.project.ram.Geocoder_location
import java.util.*
import kotlin.collections.ArrayList



class MyPostRVAdapter (
    private val applicationList: ArrayList<Post>
    ) :
    RecyclerView.Adapter<MyPostRVAdapter.ViewHolder>(), Filterable{

    lateinit var context : Context
    // 아이템 레이아웃 결합
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMyPostBinding = ItemMyPostBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        context = viewGroup.context
        return ViewHolder(binding)
    }

    // 아이템 개수
    override fun getItemCount(): Int = applicationList.size

    // view에 내용 입력
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(applicationList[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(applicationList[position])
            notifyItemChanged(position)
        }
    }

    // 레이아웃 내 view 연결
    inner class ViewHolder(val binding: ItemMyPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            var selected_random_btn : Int = 0
            var isSelected = false

            val txt_title : String = post.title
            var txt_location : String = "종로" // 아마 위도경도 계산하는 듯,,,,?
//            var txt_time : String = post.order_time
            var txt_time : String = "3시 3분"
            val txt_recruited : Int = post.recruited_num
            val txt_recruits : Int = post.num_of_recruits

            var latLong_to_address : String = Geocoder_location().calculate_location(context, post.Latitude, post.longitude)
            txt_location = latLong_to_address

//            var txt_time = Timestamp_to_SDF().convert(post.order_time)



            binding.orderListTitle.text = txt_title // 제목
            binding.orderListLocation.text = txt_location// 위치
            binding.orderListTime.text = txt_time // 주문시간
            binding.numRecruited.text = txt_recruited.toString() // 현재 사람
            binding.numRecruits.text = txt_recruits.toString() // 필요 인원

            // 수정 버튼
            binding.modifyBtn.setOnClickListener {

            }

            // 삭제 버튼
            binding.deleteBtn.setOnClickListener {

            }

            //랜덤 버튼
            binding.btnRandom.setOnClickListener {
                isSelected = !isSelected
                if(isSelected){
                    selected_random_btn++
                    binding.randomFramelayout.visibility = View.VISIBLE
                }
                else{
                    selected_random_btn--
                    binding.randomFramelayout.visibility = View.GONE
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(post: Post)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener


    // 검색
    val mDataListAll = ArrayList<Post>(applicationList)
    var mAccounts:MutableList<Post> = applicationList as MutableList<Post>
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
}