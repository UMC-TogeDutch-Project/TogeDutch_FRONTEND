package umc.mobile.project

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemDataBinding


class DataRVAdapter(private val homeDataList: ArrayList<HomeData>) : RecyclerView.Adapter<DataRVAdapter.DataViewHolder>() {

    private val checkboxStatus = SparseBooleanArray()

    //ViewHolder객체
    inner class DataViewHolder(private val viewBinding: ItemDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(homeData: HomeData) {
            viewBinding.tvTitle.text = homeData.tvTitle
            viewBinding.ivItemImageFirst.setImageResource(homeData.ivItemImageFirst!!)
            viewBinding.tvItemTitleFirst.text = homeData.tvItemTitleFirst
            viewBinding.tvItemWhereFirst.text = homeData.tvItemWhereFirst
            viewBinding.tvItemTimeFirst.text = homeData.tvItemTimeFirst
            viewBinding.ivItemImageSecond.setImageResource(homeData.ivItemImageSecond!!)
            viewBinding.tvItemTitleSecond.text = homeData.tvItemTitleSecond
            viewBinding.tvItemWhereSecond.text = homeData.tvItemWhereSecond
            viewBinding.tvItemTimeSecond.text = homeData.tvItemTimeSecond
            viewBinding.ivItemImageThird.setImageResource(homeData.ivItemImageThird!!)
            viewBinding.tvItemTitleThird.text = homeData.tvItemTitleThird
            viewBinding.tvItemWhereThird.text = homeData.tvItemWhereThird
            viewBinding.tvItemTimeThird.text = homeData.tvItemTimeThird

//            viewBinding.deleteBtn.setOnClickListener{
//                dataList.removeAt(adapterPosition)
//                notifyDataSetChanged()
//            }

        }
    }

    //ViewHolder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding =
            ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    //ViewHolder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(homeDataList[position])

    }

    //표현할 아이템의 총 갯수
    override fun getItemCount(): Int = homeDataList.size

}
