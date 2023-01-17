package umc.mobile.project.news

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemNewsUploadDataBinding

class UpLoadDataRVAdapter(private val UpLoadDataList: ArrayList<UpLoadData>) : RecyclerView.Adapter<UpLoadDataRVAdapter.DataViewHolder>() {

    private val checkboxStatus = SparseBooleanArray()

    //ViewHolder객체
    inner class DataViewHolder(private val viewBinding: ItemNewsUploadDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(upLoadData: UpLoadData) {
            viewBinding.tvDate.text = upLoadData.tvDate
            viewBinding.ivMainImage.setImageResource(upLoadData.ivMainImage!!)
            viewBinding.tvTitle.text = upLoadData.tvTitle
            viewBinding.tvUserId.text = upLoadData.tvUserId
            viewBinding.tvItemWhere.text = upLoadData.tvItemWhere
            viewBinding.tvItemTime.text = upLoadData.tvItemTime
        }
    }

    //ViewHolder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding =
            ItemNewsUploadDataBinding.inflate(LayoutInflater.from(parent.context))
        return DataViewHolder(viewBinding)
    }

    //ViewHolder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(UpLoadDataList[position])

    }

    //표현할 아이템의 총 갯수
    override fun getItemCount(): Int = UpLoadDataList.size

}
