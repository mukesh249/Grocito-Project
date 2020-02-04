package grocito.wingstud.groctiodriver.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ItemDetails(
        @SerializedName("product_name")
        val product_name: String,
        @SerializedName("product_image")
        val product_image: String,
        @SerializedName("qty")
        val qty: String
):Serializable