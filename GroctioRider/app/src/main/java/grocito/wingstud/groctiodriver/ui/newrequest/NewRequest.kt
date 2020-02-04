package grocito.wingstud.groctiodriver.ui.newrequest

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import grocito.wingstud.groctiodriver.R
import grocito.wingstud.groctiodriver.account.AccountManager
import grocito.wingstud.groctiodriver.response.AcceptResponse
import grocito.wingstud.groctiodriver.response.NewOrder
import grocito.wingstud.groctiodriver.response.SessionResponse
import grocito.wingstud.groctiodriver.rest.network.RestClient
import kotlinx.android.synthetic.main.activity_new_request.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class NewRequest : AppCompatActivity() {

    private val mp by lazy { MediaPlayer.create(this,R.raw.hudibaba) }

    private val session by lazy { AccountManager.getUserAccount()!! }

    private val newOrder by lazy {
        intent.getSerializableExtra("newOrder") as NewOrder
    }

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_new_request)

        this.setFinishOnTouchOutside(false)

        val am = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        am.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0
        )

        datetime_tv.text = newOrder.deliveryDatetime
        order_id.text = newOrder.orderNo
        amount.text = "₹ ${newOrder.amount}"
        payment_mode.text = newOrder.paymentType
        userAddress.text = newOrder.address
        restaurant_name.text = newOrder.seller_name
        restaurant_address.text = newOrder.seller_address
        accept.setOnClickListener {
            accept(newOrder.orderId,"accepted",newOrder.seller_id)
        }
        reject.setOnClickListener {
            accept(newOrder.orderId,"rejected",newOrder.seller_id)
        }

        handler.postDelayed(delayedRunnable,30000)
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mp.isLooping = true
        mp.start()
    }

    private val delayedRunnable = Runnable {
        mp.stop()
        finish()
    }

    companion object {
        private val TAG = "NewRequestActivity"
    }

    fun accept(orderId : String,status : String,sellerId :String){
        mp.stop()
        handler.removeCallbacks(delayedRunnable)
        loading.visibility = View.VISIBLE
        val hashMap: HashMap<String, String> = HashMap()
        hashMap["user_id"] = session.deliverBoyId
        hashMap["status"] = status
        hashMap["order_id"] = orderId
        hashMap["seller_id"] = sellerId
        RestClient.getInstance().apiInterface.acceptNewOrder(hashMap).enqueue(object : Callback<AcceptResponse> {
            override fun onFailure(call: Call<AcceptResponse>, t: Throwable) {
                loading.visibility = View.GONE
                Log.d(TAG, "onFailure", t)
                toast("Opps! something went wrong")
                finish()
            }

            override fun onResponse(call: Call<AcceptResponse>, response: Response<AcceptResponse>) {
                loading.visibility = View.GONE
                if (response.isSuccessful && response.body()!!.status_code == 1) {
                    toast(response.body()!!.message.toString())
                    finish()
                } else {
                    if (response.body()!!.error_message != null) {
                        toast(response.body()!!.error_message!!)
                    } else {
                        toast("Opps! something went wrong")
                    }
                }
            }
        })
    }
//    fun reject(orderId: String) {
//        mp.stop()
//        handler.removeCallbacks(delayedRunnable)
//        loading.visibility = View.VISIBLE
//        val hashMap: HashMap<String, String> = HashMap()
//        hashMap["delivery_boy_id"] = session.deliverBoyId
////        hashMap["auth_key"] = session.authKey
//        hashMap["order_id"] = orderId
//        RestClient.getInstance().apiInterface.rejectNewOrder(hashMap).enqueue(object : Callback<SessionResponse> {
//            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
//                loading.visibility = View.GONE
//                Log.d(TAG, "onFailure", t)
//                toast("Opps! something went wrong")
//                finish()
//            }
//
//            override fun onResponse(call: Call<SessionResponse>, response: Response<SessionResponse>) {
//                loading.visibility = View.GONE
//                if (response.isSuccessful && response.body()!!.status == "success") {
//                    toast("Request rejected")
//                    finish()
//                } else {
//                    if (response.body()!!.message != null) {
//                        toast(response.body()!!.message!!)
//                    } else {
//                        toast("Opps! something went wrong")
//                    }
//                }
//            }
//        })
//    }

}
