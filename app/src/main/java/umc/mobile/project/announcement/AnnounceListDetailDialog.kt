package umc.mobile.project.announcement

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import umc.mobile.project.AnnouncePostActivity
import umc.mobile.project.R
import java.security.AccessController.getContext

class AnnounceListDetailDialog(context: Context) {

        private val dlg = Dialog(context)
        //private val dlg2 = PlaceSearchDialog(context)
        private lateinit var btn_back : ImageButton
        private lateinit var image_btn_map : ImageButton

        fun start(){
            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dlg.setContentView(R.layout.activity_announce_detail)
            dlg.setCanceledOnTouchOutside(false)       //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 설정
            dlg.setCancelable(true)    // 취소가 가능하도록 하는 코드

            btn_back = dlg.findViewById(R.id.back_btn)
            image_btn_map = dlg.findViewById(R.id.image_btn_map)
            btn_back.setOnClickListener {
                dlg.dismiss()
            }

            image_btn_map.setOnClickListener {
//               dlg2.start()
//                PlaceSearchDialogFragment().show(
//                    parentFragmentManager, "PlaceSearchDialog"
//                )
//                val intent = Intent(getContext(), PlaceSearchActivity::class.java)
//                getApplicationContext().startActivity(intent)
            }

            dlg.show()
        }


    }
