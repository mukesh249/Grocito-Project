package grocito.wingstud.groctiodriver.rest.network

import grocito.wingstud.groctiodriver.response.*
import grocito.wingstud.groctiodriver.rest.requests.HistoryOrder
import grocito.wingstud.groctiodriver.rest.requests.LoginRequest
import grocito.wingstud.groctiodriver.rest.requests.OrderListResponse
import grocito.wingstud.groctiodriver.rest.responses.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {


    @POST("r-api-login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
//
//    @POST("forget_password_mobile")
//    fun forgot(@Body param: HashMap<String, String>): Call<BaseResponse>
//
//    @POST("forget_password_otp")
//    fun verifyOTP(@Body body: HashMap<String, String>): Call<BaseResponse>
//
//    @POST("resend_otp")
//    fun resendOTP(@Body hashmap: HashMap<String, String>): Call<BaseResponse>
//
//    @POST("change_password")
//    fun changePassword(@Body data: HashMap<String, String>): Call<BaseResponse>
//
    @POST("change-status")
    fun changeOnlineStatus(@Body data: HashMap<String, String>): Call<ChangeStatusResponse>

//    @POST("new_orders")
//    fun newOrders(@Body delivery_boy_id: String, auth_key: String): Call<OrderListResponse<NewOrder>>
//
//    @POST("assigned_orders")
//    fun assignedOrder(@Body riderRequest: RiderRequest): Call<OrderListResponse<AssignedOrder>>
//
    @POST("dashboard")
    fun dashBoadData(@Body hashmap: HashMap<String, String>): Call<HomeResponse>
//
    @POST("get-order-history")
    fun completed(@Body hashmap: HashMap<String, String>): Call<OrderListResponse<HistoryOrder>>
//
//    @POST("rejected_orders")
//    fun rejectedOrders(@Body riderRequest: RiderRequest): Call<OrderListResponse<HistoryOrder>>
//
//    //   http://ss.roketvending.com/demonew/hudibaba/Hudibaba_delivery_boy/delivery_boy_device_fcm
//
    @POST("delivery_boy_device_fcm")
    fun updateGCMToken(@Body hashmap: HashMap<String, String>): Call<SessionResponse>
//
//    @POST("food_delivery_code")
//    fun foodDeliveryCode(@Body hashmap: HashMap<String, String>): Call<BaseResponse>
//
//
    @POST("accept-order")
    fun acceptNewOrder(@Body hashmap: HashMap<String, String>): Call<AcceptResponse>

    @POST("assigned-order")
    fun assignedorderList(@Body hashmap: HashMap<String, String>): Call<OrderListResponse<AssignedOrder<ItemDetails>>>
//
    @POST("food_delivery_code")
    fun completeDelivery(@Body hashmap: HashMap<String, String>): Call<SessionResponse>
//
//    @POST("status")
//    fun checkOnlineStatus(@Body hashmap: HashMap<String, String>): Call<StatusResponse>
//
    @POST("your-earning")
    fun earningDetails(@Body hashmap: HashMap<String, String>): Call<EarningResponse<DataEarning>>
//
//    @POST("todays_orders")
//    fun todayOrder(@Body hashmap: HashMap<String, String>): Call<TodayOrderResponse>
//
//    @POST("paid_payment")
//    fun paidPayment(@Body hashmap: HashMap<String, String>): Call<PaidResponse>
//
//    @POST("order_list")
//    fun orderList(@Body hashmap: HashMap<String, String>): Call<PaidViewDetailResponse>
//
//    @POST("cod_list")
//    fun codList(@Body hashmap: HashMap<String, String>): Call<CODResponse>
//
//    @POST("order_details")
//    fun orderDetails(@Body hashmap: HashMap<String, String>): Call<JsonObject>
//
//    @POST("unpaid_payment")
//    fun unpaidPayment(@Body hashmap: HashMap<String, String>): Call<PaidResponse>
//
//    @POST("todays_orders")
//    fun todayPayment(@Body hashmap: HashMap<String, String>): Call<TodayOrderResponse>
//
//    @POST("free_hours")
//    fun freeHours(@Body hashmap: HashMap<String, String>): Call<BaseResponse>


    @POST("app_session_status")
    fun session(@Body hashmap: HashMap<String, String>): Call<SessionResponse>


}