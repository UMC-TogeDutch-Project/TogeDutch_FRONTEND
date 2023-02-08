package umc.mobile.project.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.OrderListAdapterBinding


class OrderRVAdapter (private val orderList: ArrayList<OrderData>): RecyclerView.Adapter<OrderRVAdapter.ViewHolder>() {

    // 보여줄 아이템 개수만큼 View를 생성 (RecyclerView가 초기화 될 때 호출)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): OrderRVAdapter.ViewHolder {
        val binding: OrderListAdapterBinding = OrderListAdapterBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )
        return ViewHolder(binding)
    }

    // 보여줄 아이템 개수
    override fun getItemCount(): Int = orderList.size

    // 생성된 View에 보여줄 데이터를 설정
    override fun onBindViewHolder(holder: OrderRVAdapter.ViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    // ViewHolder 단위 객체로 View의 데이터를 설정
    inner class ViewHolder(val binding: OrderListAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderData: OrderData) {
            binding.orderListTitle.text = orderData.title
            binding.orderListLocation.text = orderData.place
            binding.orderListTime.text = orderData.time
            binding.score.text = orderData.score.toString()

            binding.listItemScore.setOnClickListener {
//                val dialog = ReviewPopupDialog(this)
//                dialog.start()

            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(order: OrderData)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener
}