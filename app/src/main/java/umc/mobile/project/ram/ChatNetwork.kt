package umc.mobile.project.ram

import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger

class ChatNetwork{
    val logger = Logger.getLogger("Main")

    lateinit var stompConnection: Disposable
    lateinit var topic: Disposable

    val url = "ws://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/"
    val intervalMillis = 1000L // stomp의 연결이 끊길 시 주기적으로 몇 초 간격으로 연결 시도할건지
    val client = OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()

    val stomp = StompClient(client, intervalMillis).apply { this@apply.url = url } // 생명주기 관리

    fun runStomp(){
        stompConnection = stomp.connect().subscribe{
            when(it.type){
                Event.Type.OPENED -> {
                    //subscribe
                    topic = stomp.join("").subscribe { logger.log(Level.INFO, it) }

                    // unsubscribe


                    //send
                }
                Event.Type.CLOSED -> {

                }
                Event.Type.ERROR -> {

                }
                else -> {}
            }
        }

        val scanner = Scanner(System.`in`)
        scanner.nextLine()

    }


}