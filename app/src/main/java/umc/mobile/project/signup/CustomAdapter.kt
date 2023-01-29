package umc.mobile.project.signup


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import umc.mobile.project.R



class CustomAdapter (
    private val context: Context, private val dataList: ArrayList<DataVo>
    ): RecyclerView.Adapter<CustomAdapter.ItemViewHolder>() {

    var mPosition = 0

    fun getPosition(): Int{
        return mPosition
    }

    fun setPosition(position: Int){
        mPosition = position
    }

    fun addItem(dataVo: DataVo){
        dataList.add(dataVo)

        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        if(position > 0){
            dataList.removeAt(position)

            notifyDataSetChanged()
        }
    }

    inner class ItemViewHolder(itemView: View):
            RecyclerView.ViewHolder(itemView){

        private val keyWord = itemView.findViewById<TextView>(R.id.etInputAlarmKeyword)

        fun bind(dataVo: DataVo, context: Context){

            keyWord.text = dataVo.tvKeywordTitle

        }
    }

    //ViewHolder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout, parent, false)
        return ItemViewHolder(view)
    }

    //ViewHolder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position], context)
        holder.itemView.setOnClickListener{
            view -> setPosition(position)
        }
    }

    //표현할 아이템의 총 갯수
    override fun getItemCount(): Int {
        return dataList.size
    }

}