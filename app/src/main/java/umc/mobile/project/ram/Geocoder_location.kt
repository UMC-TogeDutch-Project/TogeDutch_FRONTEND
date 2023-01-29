package umc.mobile.project.ram

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import java.io.IOException
import java.util.Locale


class Geocoder_location {
    fun calculate_location(context : Context, latitude : Double, longtitude : Double) : String{
        var nowAddress : String = "현재 위치를 확인할 수 없습니다."
        val geocoder = Geocoder(context, Locale.KOREA)
        var address : List<Address>
        try{
            if(geocoder != null){
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                address = geocoder.getFromLocation(latitude, longtitude, 1)
                if(address != null && address.size >0){
                    // 주소 받아오기
                    var currentLocationAddress = address.get(0).getAddressLine(0).toString()
                    nowAddress = currentLocationAddress
                }
            }
        }catch (e : IOException){
            Toast.makeText(context,"주소를 가져 올 수 없습니다.", Toast.LENGTH_LONG).show();
        }

        return nowAddress

    }
}