package grocito.wingstud.groctiodriver.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AssignedOrder<T>(
        @SerializedName("id")
        val id: String,
        @SerializedName("order_id")
        val order_id: String,
        @SerializedName("payment_mode")
        val payment_mode: String,
        @SerializedName("delivery_date")
        val delivery_date: String,
        @SerializedName("delivery_time")
        val delivery_time: String,
        @SerializedName("seller_name")
        val seller_name: String,
        @SerializedName("user_lat")
        val user_lat: String,
        @SerializedName("user_long")
        val user_long: String,
        @SerializedName("seller_lat")
        val seller_lat: String,
        @SerializedName("seller_long")
        val seller_long: String,
        @SerializedName("user_mobile")
        val user_mobile: String,
        @SerializedName("user_name")
        val user_name: String,
        @SerializedName("user_address")
        val user_address: String,
        @SerializedName("seller_id")
        val seller_id: String,
       @SerializedName("seller_address")
        val seller_address: String ,
        val data: List<T>?
):Serializable