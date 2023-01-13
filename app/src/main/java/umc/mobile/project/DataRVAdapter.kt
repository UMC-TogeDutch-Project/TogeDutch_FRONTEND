package umc.mobile.project

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemDataBinding


class DataRVAdapter(private val dataList: ArrayList<Data>) : RecyclerView.Adapter<DataRVAdapter.DataViewHolder>() {

    private val checkboxStatus = SparseBooleanArray()

    //ViewHolder객체
    inner class DataViewHolder(private val viewBinding: ItemDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(data: Data) {
            viewBinding.tvTitle.text = data.tvTitle
            viewBinding.ivItemImageFirst.setImageResource(data.ivItemImageFirst!!)
            viewBinding.tvItemTitleFirst.text = data.tvItemTitleFirst
            viewBinding.tvItemWhereFirst.text = data.tvItemWhereFirst
            viewBinding.tvItemTimeFirst.text = data.tvItemTimeFirst
            viewBinding.ivItemImageSecond.setImageResource(data.ivItemImageSecond!!)
            viewBinding.tvItemTitleSecond.text = data.tvItemTitleSecond
            viewBinding.tvItemWhereSecond.text = data.tvItemWhereSecond
            viewBinding.tvItemTimeSecond.text = data.tvItemTimeSecond
            viewBinding.ivItemImageThird.setImageResource(data.ivItemImageThird!!)
            viewBinding.tvItemTitleThird.text = data.tvItemTitleThird
            viewBinding.tvItemWhereThird.text = data.tvItemWhereThird
            viewBinding.tvItemTimeThird.text = data.tvItemTimeThird

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
        holder.bind(dataList[position])

    }

    //표현할 아이템의 총 갯수
    override fun getItemCount(): Int = dataList.size

}
