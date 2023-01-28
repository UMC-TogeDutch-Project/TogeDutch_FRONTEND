package umc.mobile.project.signup

import android.content.Context
import android.util.SparseBooleanArray
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import umc.mobile.project.databinding.ViewItemLayoutBinding
//
//
//class CustomAdapter (private val dataList: ArrayList<DataVo>): RecyclerView.Adapter<CustomAdapter.ItemViewHolder>() {
//
//    private val checkboxStatus = SparseBooleanArray()
//
//    inner class ItemViewHolder(private val viewBinding: ViewItemLayoutBinding):
//            RecyclerView.ViewHolder(viewBinding.root){
//        fun bind(dataVo: DataVo, context: Context){
//            viewBinding.tvKeywordTitle.text = dataVo.tvKeywordTitle
//        }
//    }
//
//    //ViewHolder 만들어질 때 실행할 동작
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        val viewBinding =
//           ViewItemLayoutBinding.inflate(LayoutInflater.from(parent.context))
//        return ItemViewHolder(viewBinding)
//    }
//
//    //ViewHolder가 실제로 데이터를 표시해야 할 때 호출되는 함수
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        holder.bind(dataList[position])
//
//
//    }
//
//    //표현할 아이템의 총 갯수
//    override fun getItemCount(): Int = dataList.size
//
//
//
//
//}