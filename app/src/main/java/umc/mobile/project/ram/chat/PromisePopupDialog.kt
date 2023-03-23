package umc.mobile.project.ram.chat

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import umc.mobile.project.R
import umc.mobile.project.databinding.CustomDialogBinding
import umc.mobile.project.databinding.PromisePopupDialogBinding
import umc.mobile.project.login.MyCustomDialogInterface


class PromisePopupDialog(context: Context) : Dialog(context) {
    var dlg_meet_time_txt = ""
    private val dlg = Dialog(context)
    private lateinit var btn_close : Button
    private lateinit var btn_send : Button
    private lateinit var binding : PromisePopupDialogBinding

    private var editText6: EditText? = null
    private var editText7: EditText? = null
    private var editText8: EditText? = null
    private var editText9: EditText? = null
    private var editText10: EditText? = null
    private var editText11: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PromisePopupDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        start()
    }
    fun start(){

        btn_close = binding.closeBtn
        btn_close.setOnClickListener {
            dlg.dismiss()
        }

        editText6 = binding.dialogAnnEtYear
        editText7 = binding.dialogAnnEtMonth
        editText8 = binding.dialogAnnEtDay
        editText9 = binding.dialogAnnEtTime
        editText10 = binding.dialogAnnEtHour
        editText11 = binding.dialogAnnEtMinute


        btn_send = binding.meetTimeSendBtn
        btn_send.setOnClickListener {
            dlg_meet_time_txt = string_to_timestamp(editText6!!.text.toString(),editText7!!.text.toString(), editText8!!.text.toString(), editText9!!.text.toString()
                ,editText10!!.text.toString(), editText11!!.text.toString())



            Log.d("dlg_시간  : ", dlg_meet_time_txt)
            onClickListener.onClicked(dlg_meet_time_txt)
            dlg.dismiss()
        }


        dlg.show()
    }

    interface ButtonClickListener{
        fun onClicked(text: String)
    }

    private lateinit var onClickListener: ButtonClickListener

    fun setOnClickListener(listener: ButtonClickListener){
        onClickListener = listener
    }

    private fun string_to_timestamp(year :String, month: String, day : String, am_pm : String, hour : String, minute : String) : String{
        var hour_int = 0
        var hour_text = ""

        // 01, 02 이런 식으로 들어왔을 때
        if(hour.substring(0).equals("0")){
            // 1의 자리만 substring
            hour_int = hour.substring(1).toInt()
            if(am_pm.equals("오후") && hour.toInt() != 12){
                hour_int = hour.toInt() + 12
                hour_text = hour_int.toString()
            }
            else{
                hour_text = "0" + hour
            }
        }else{
            if(am_pm.equals("오후") && hour.toInt() != 12){
                hour_int = hour.toInt() + 12
                hour_text = hour_int.toString()
            }
            else{
                hour_text = hour
            }
        }

        var set = "2022-01-23T03:34:56.000+00:00"
//        var order_time = year + "-" + month + "-" + day + "T" + hour_int + ":" + minute + ":" + "00.000+00:00"
        var order_time = year + month + day + hour_text + minute + "00"

        return order_time
    }

}