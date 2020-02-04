package grocito.wingstud.groctiodriver.response

class EarningResponse<T> (
        val status_code : Int,
        val message : String,
        val data : List<T>,
        val error_message : String
)
class DataEarning(
        val id : String,
        val order_id : String,
        val payment_mode : String,
        val delivery_date : String,
        val delivery_time : String,
        val seller_name : String,
        val seller_lat : String,
        val seller_address : String,
        val seller_long : String,
        val user_lat : String,
        val user_long : String,
        val user_mobile : String,
        val user_name : String,
        val user_address : String
)