package umc.mobile.project


import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemDataBinding


class DataRVAdapter(private val homeDataList: ArrayList<HomeData>) : RecyclerView.Adapter<DataRVAdapter.RecentViewHolder>() {

    private val checkboxStatus = SparseBooleanArray()

    //ViewHolder 객체
    inner class RecentViewHolder(val viewBinding: ItemDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(homeData: HomeData) {
            viewBinding.tvItemTitle.text = homeData.title //제목
            viewBinding.tvItemWhere.text = "주소" // 주소
            viewBinding.tvItemTime.text = homeData.order_time // 주문한 시간
            viewBinding.annApp.text = homeData.recruited_num.toString() // 신청인원
            viewBinding.annRecruit.text = homeData.num_of_recruits.toString() //총 인원

        }

    }

    //ViewHolder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DataRVAdapter.RecentViewHolder {

        val viewBinding: ItemDataBinding = ItemDataBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        return RecentViewHolder(viewBinding)

    }

    //ViewHolder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: DataRVAdapter.RecentViewHolder, position: Int) {

        holder.bind(homeDataList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(homeDataList[position])
            notifyItemChanged(position)
        }


    }

    //표현할 아이템의 총 갯수
    override fun getItemCount(): Int = homeDataList.size


    //2
    interface OnItemClickListener {
        fun onItemClick(homeData: HomeData)

    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener


}
