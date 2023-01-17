package umc.mobile.project

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemNewsMateDataBinding

class MateDataRVAdapter (private val MateDataList: ArrayList<MateData>) : RecyclerView.Adapter<MateDataRVAdapter.DataViewHolder>() {

    private val checkboxStatus = SparseBooleanArray()

    //ViewHolder객체
    inner class DataViewHolder(private val viewBinding: ItemNewsMateDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(mateData: MateData) {
            viewBinding.tvDate.text = mateData.tvDate
            viewBinding.ivMainImage.setImageResource(mateData.ivMainImage!!)
            viewBinding.tvTitle.text = mateData.tvTitle
            viewBinding.tvUserId.text = mateData.tvUserId

        }
    }

    //ViewHolder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateDataRVAdapter.DataViewHolder {
        val viewBinding =
            ItemNewsMateDataBinding.inflate(LayoutInflater.from(parent.context))
        return DataViewHolder(viewBinding)
    }

    //ViewHolder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(MateDataList[position])

    }

    //표현할 아이템의 총 갯수
    override fun getItemCount(): Int = MateDataList.size

}