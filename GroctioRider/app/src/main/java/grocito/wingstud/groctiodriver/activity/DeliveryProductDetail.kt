package grocito.wingstud.groctiodriver.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import grocito.wingstud.groctiodriver.R
import grocito.wingstud.groctiodriver.account.AccountManager
import grocito.wingstud.groctiodriver.response.AssignedOrder
import grocito.wingstud.groctiodriver.response.ItemDetails
import grocito.wingstud.groctiodriver.response.SessionResponse
import grocito.wingstud.groctiodriver.rest.network.RestClient
import grocito.wingstud.groctiodriver.util.BaseURL
import grocito.wingstud.groctiodriver.util.Constants
import grocito.wingstud.groctiodriver.util.toast
import kotlinx.android.synthetic.main.activity_delivery_product_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeliveryProductDetail : AppCompatActivity() {

    val session by lazy { AccountManager.getUserAccount()!! }
    var orderDetails: AssignedOrder<ItemDetails>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_product_detail)

        if (intent.getSerializableExtra("orderDetail")!=null){
            orderDetails = intent.getSerializableExtra("orderDetail")!! as AssignedOrder<ItemDetails>
            order_no.text = "${orderDetails?.order_id}"
            address.text = orderDetails?.user_address
            mob.text = orderDetails?.user_mobile
            payment_type.text = orderDetails?.payment_mode
            username.text = orderDetails?.user_name
//            items_tv.text = orderDetails?.data!![0].product_name
//            price_tv.text = "₹ ${orderDetails?.amount}"
//            Glide.with(this).load(BaseURL+orderDetails?.data!![0].product_image)
//                    .into(image_tv)

            map.setOnClickListener {
                startActivity(Intent(this, LiveTrackActivity::class.java)
                        .apply {
                            putExtra("assignedOrder", orderDetails)
                            putExtra("userTrack", true)
                        })
            }

            call.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${orderDetails?.user_mobile}")
                startActivity(intent)
            }
//            pickUpCode.text = "Order pickup code- ${orderDetails?.pickupCode}"

            otp_enter.setOnClickListener {
                CompleteDelivery(orderDetails!!.id,orderDetails!!.seller_id,enterOtpEt.text.toString())
            }
        }
        back_iv.setOnClickListener { onBackPressed() }
    }

    private fun CompleteDelivery(orderId:String,sellerId:String,otpCode:String){
        val hashMap: HashMap<String, String> = HashMap()
        hashMap["user_id"] = session.deliverBoyId
        hashMap["order_id"] = orderId
        hashMap["seller_id"] = sellerId
        hashMap["code"] = otpCode
        RestClient.getInstance().apiInterface.completeDelivery(hashMap).enqueue(object : Callback<SessionResponse>{
            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<SessionResponse>, response: Response<SessionResponse>) {
                if (response.isSuccessful){
                    
                }else{

                }

            }
        })
    }
}
