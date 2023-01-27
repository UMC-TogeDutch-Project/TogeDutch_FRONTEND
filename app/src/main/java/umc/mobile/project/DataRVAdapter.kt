package umc.mobile.project


import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemDataBinding
import umc.mobile.project.databinding.ItemDataSectionBinding


class DataRVAdapter(private val homeDataList: ArrayList<HomeData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val checkboxStatus = SparseBooleanArray()

    //ViewHolder 객체
   inner class DataViewHolder(private val viewBinding: ItemDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(item: HomeData.Item) {
            viewBinding.tvItemTitle.text = item.title //제목
            viewBinding.tvItemWhere.text = "주소" // 주소
            viewBinding.tvItemTime.text = item.order_time // 주문한 시간
            viewBinding.annApp.text = item.recruited_num.toString() // 신청인원
            viewBinding.annRecruit.text = item.num_of_recruits.toString() //총 인원

        }

    }
    //ViewHolder 객체
    inner class HeaderViewHolder(private val viewBinding: ItemDataSectionBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(header: HomeData.Header) {
            viewBinding.rvCategory.text = header.category

        }

    }



    //ViewHolder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val viewBinding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return when(viewType){
            TYPE_ITEM -> DataViewHolder(ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            TYPE_HEADER -> HeaderViewHolder(ItemDataSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    //ViewHolder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is DataViewHolder -> holder.bind(homeDataList[position] as HomeData.Item)
            is HeaderViewHolder -> holder.bind(homeDataList[position] as HomeData.Header)

        }
        holder.itemView.setOnClickListener() {
            itemClickListener1.onItemClick1(homeDataList[position] as HomeData.Item)
            notifyItemChanged(position)
        }
        holder.itemView.setOnClickListener() {
            itemClickListener2.onItemClick2(homeDataList[position] as HomeData.Header)
            notifyItemChanged(position)
        }


    }

    //표현할 아이템의 총 갯수
    override fun getItemCount(): Int = homeDataList.size

    override fun getItemViewType(position: Int): Int {
        return when(homeDataList[position]){
            is HomeData.Item -> TYPE_ITEM
            is HomeData.Header -> TYPE_HEADER
            else -> throw IllegalArgumentException("Invalid Item")
        }
    }
    fun updateList(updatedList: List<HomeData>){
        homeDataList.clear()
        homeDataList.addAll(updatedList)
        notifyDataSetChanged()
    }


    //1
    interface OnItemClickListener1 {
        fun onItemClick1(item: HomeData.Item)

    }

    fun setItemClickListener1(onItemClickListener1: OnItemClickListener1) {
        this.itemClickListener1 = onItemClickListener1
    }

    private lateinit var itemClickListener1 : OnItemClickListener1

    //2
    interface OnItemClickListener2 {
        fun onItemClick2(header: HomeData.Header)

    }
    fun setItemClickListener2(onItemClickListener2: OnItemClickListener2) {
        this.itemClickListener2 = onItemClickListener2
    }

    private lateinit var itemClickListener2 : OnItemClickListener2

    companion object{
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }
}
