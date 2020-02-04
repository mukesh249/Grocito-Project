package grocito.wingstud.groctiodriver.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.tbruyelle.rxpermissions2.RxPermissions
import grocito.wingstud.groctiodriver.App
import grocito.wingstud.groctiodriver.R
import grocito.wingstud.groctiodriver.Services.LocationService
import grocito.wingstud.groctiodriver.account.AccountManager
import grocito.wingstud.groctiodriver.activity.DeliveryProductDetail
import grocito.wingstud.groctiodriver.activity.LiveTrackActivity
import grocito.wingstud.groctiodriver.databinding.FragmentHomeBinding
import grocito.wingstud.groctiodriver.extensions.defaultSharedPreferences
import grocito.wingstud.groctiodriver.extensions.putInt
import grocito.wingstud.groctiodriver.response.AssignedOrder
import grocito.wingstud.groctiodriver.response.ChangeStatusResponse
import grocito.wingstud.groctiodriver.response.HomeResponse
import grocito.wingstud.groctiodriver.response.ItemDetails
import grocito.wingstud.groctiodriver.rest.network.RestClient
import grocito.wingstud.groctiodriver.rest.requests.OrderListResponse
import grocito.wingstud.groctiodriver.util.toast
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() ,AssignedOrderAdapter.OrderTrackListener{


    override fun onSellerTrack(assignedOrder: AssignedOrder<ItemDetails>) {
        startActivity(Intent(activity, LiveTrackActivity::class.java)
                .apply {
                    putExtra("assignedOrder", assignedOrder)
                    putExtra("userTrack", false)
                })
    }

    override fun onUserTrack(assignedOrder: AssignedOrder<ItemDetails>) {
        startActivity(Intent(activity, LiveTrackActivity::class.java)
                .apply {
                    putExtra("assignedOrder", assignedOrder)
                    putExtra("userTrack", true)
                })
    }

    override fun onOrderDetail(assignedOrder: AssignedOrder<ItemDetails>) {
        startActivity(Intent(activity, DeliveryProductDetail::class.java).apply {
            putExtra("orderDetail", assignedOrder)
        })
    }

    //private fun
    companion object {
        val TAG = "HomeFragment"
    }
    private lateinit var homeViewModel: HomeViewModel
    private var forceRefresh = false
    private var isRefreshing = false
    private val session by lazy { AccountManager.getUserAccount()!! }
    lateinit var binding: FragmentHomeBinding

    private val assignedOrderAdapter: AssignedOrderAdapter by lazy {
        AssignedOrderAdapter().apply { this.trackListener = this@HomeFragment }
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        checkPermissions()
//        getToken()
        binding.statusSw.setOnClickListener {
            getDeviceLocation()
        }
        binding.relEmptyWL.visibility = View.VISIBLE
        binding.assignedOrderRv.layoutManager = LinearLayoutManager(activity)
        binding.assignedOrderRv.adapter = assignedOrderAdapter
        loadAssignedOrder()

        binding.swipeView.setOnRefreshListener {
            if (!isRefreshing) {
                forceRefresh = true
                binding.swipeView.isRefreshing = true
                dashBoardData()
                loadAssignedOrder()
            }
        }
        return binding.getRoot()
    }

    private var locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d(TAG, "onLocationChanged: $location")
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            Log.d(TAG, "onStatusChanged: $provider $status $extras")
        }

        override fun onProviderEnabled(provider: String) {
            Log.d(TAG, "onProviderEnabled: $provider")
        }

        override fun onProviderDisabled(provider: String) {
            Log.d(TAG, "onProviderDisabled: $provider")
        }
    }

    private fun getDeviceLocation() {
        try {
            val locationResult = LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation
            locationResult.addOnSuccessListener { location ->
                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
//                    val status = if (status_sw.isChecked)
                    changeOnlineStatus()

                } else {

                    val locationManager =
                            context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0,
                            0f,
                            locationListener
                    )
                    getDeviceLocation()
                }

            }

        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message)
        }
    }
    private fun changeOnlineStatus() {
        Log.d("HomeFragment", "changeOnlineStatus")
        val hashMap = HashMap<String, String>()
        hashMap["user_id"] = session.deliverBoyId

        RestClient.getInstance().apiInterface.changeOnlineStatus(hashMap)
                .enqueue(object : Callback<ChangeStatusResponse> {
                    override fun onFailure(call: Call<ChangeStatusResponse>, t: Throwable) {
                        context?.toast("Opps! Network unavailable")
                        binding.statusSw.isEnabled = !status_sw.isEnabled
                    }

                    override fun onResponse(
                            call: Call<ChangeStatusResponse>,
                            response: Response<ChangeStatusResponse>
                    ) {
                        if (response.isSuccessful && response.body()!!.status_code == 1) {
                            App.get().defaultSharedPreferences.putInt("status", response.body()!!.status)
                            if (response.body()!!.status == 1) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    context?.startForegroundService(Intent(
                                            context,
                                            LocationService::class.java
                                    ))
                                    context?.startForegroundService(
                                            Intent(
                                                    context,
                                                    LocationService::class.java
                                            )
                                    )
                                } else {
                                    context?.startService(
                                            Intent(
                                                    context,
                                                    LocationService::class.java
                                            )
                                    )
                                }
                                Snackbar.make(root, "You are online", Snackbar.LENGTH_LONG)
                                        .show()
                                status_sw.isChecked = true
                            } else {
                                context?.stopService(Intent(context, LocationService::class.java))
                                Snackbar.make(root, "You are offline", Snackbar.LENGTH_LONG).show()
                                status_sw.isChecked = false
                            }
                        } else {
                            if (response.body()!!.message != null) {
                                context?.toast(response.body()!!.message)
                            } else {
                                context?.toast("Opps! Something went wrong")
                            }
                            binding.statusSw.isChecked = !status_sw.isEnabled
                        }
                    }
                })
    }

    @SuppressLint("CheckResult")
    fun checkPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            RxPermissions(this).request(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            ).subscribe { isGranted ->
                if (isGranted) {
                    checkLocationSetting()
                } else {
                    context?.toast("You can't go further without permission.")
                    checkPermissions()
                }
            }
        } else {
            checkLocationSetting()
        }
    }
    private var mLocationPermissionGranted: Boolean = false
    private val requestCheckLocationSetting: Int = 199
    private fun checkLocationSetting() {
        val locationRequest = LocationRequest.create().apply {
            this.interval = 30000;
            this.fastestInterval = 5000
            this.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationSettingRequest = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .build()
        val task = LocationServices.getSettingsClient(requireContext()).checkLocationSettings(locationSettingRequest)
        task.addOnCompleteListener {
            try {
                it.getResult(ApiException::class.java)
                dashBoardData()
                mLocationPermissionGranted = true
            } catch (exception: ApiException) {
                //Location setting is not enabled show Location Dialog
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        // Location settings are not satisfied. But could be fixed by showing the LOCATION  dialog.
                        try {
                            // Cast to a resolvable exception.
                            val resolvable = exception as ResolvableApiException
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(requireActivity(), requestCheckLocationSetting)

                        } catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }
                    }
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCheckLocationSetting && resultCode == Activity.RESULT_OK) {
            mLocationPermissionGranted = true
            //startService()
            dashBoardData()
        } else {/// else show the message and show dialog again
            checkLocationSetting()
        }
    }

    private fun dashBoardData() {
//        progress_bar.visibility = View.VISIBLE

        val hashMap: HashMap<String, String> = HashMap()
//        hashMap["auth_key"] = session.authKey
        hashMap["user_id"] = session.deliverBoyId

        RestClient.getInstance().apiInterface.dashBoadData(hashMap).enqueue(object :
                Callback<HomeResponse> {
            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                forceRefresh = false
                isRefreshing = false
                binding.swipeView.isRefreshing = false
                //                progress_bar.visibility = View.GONE
                Snackbar.make(root, "Network Error", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<HomeResponse>,
                    response: Response<HomeResponse>
            ) {
                forceRefresh = false
                isRefreshing = false
                binding.swipeView.isRefreshing = false
//                progress_bar.visibility = View.GONE
                if (response.isSuccessful) {
                    val body = response.body()
//                    if (body!!.status == "success"){
//                        salaryAmt.text = body.totay_payment
                    binding.txtTodayOrder.text = if (body?.totay_order_count.isNullOrEmpty()) {
                        "Rs. 0"
                    } else {
                        "${body?.totay_order_count}"
                    }
                    binding.logintimeTv.text = "Login Time : ${body?.login_time}"

                    binding.txtTodayPayment.text = if (body?.totay_payment.isNullOrEmpty()) {
                        "Rs. 0"
                    } else {
                        "Rs. ${body?.totay_payment}"
                    }
                    if (body?.cod_amount.isNullOrEmpty()) {
                        binding.codAmt.text = "Rs. 0"
                    } else {
                        binding.codAmt.text = "Rs. ${body?.cod_amount}"
                        if (1500 <= body?.cod_amount?.toDouble()!!) {
                            codAmtMsgTv.setTextColor(Color.RED)
                        }
                    }

                    binding.distanceTv.text = if (body?.last_ride?.estimated_distance.isNullOrEmpty()) {
                        "0km"
                    } else {
                        "${body?.last_ride?.estimated_distance}km"
                    }
                    binding.salaryAmt.text = if (body?.last_ride?.total_amount.isNullOrEmpty()) {
                        "Rs. 0"
                    } else {
                        String.format(" Rs. %.2f", body?.last_ride?.total_amount?.toBigDecimal())
                    }

                    val serviceStarted = LocationService.isServiceStarted
                    if (!serviceStarted) {
                        val onlineStatus = response.body()!!.status
                        if (onlineStatus == 1) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                activity?.startForegroundService(
                                        Intent(
                                                activity,
                                                LocationService::class.java
                                        )
                                )
                            } else {
                                activity?.startService(
                                        Intent(
                                                activity,
                                                LocationService::class.java
                                        )
                                )
                            }

                            binding.statusSw.isChecked = true
                        }
                    }else{
                        binding.statusSw.isChecked = true
                    }

                }
            }
        })
    }

    private fun loadAssignedOrder() {

        Log.i(
                "dfasfdasfd", session.deliverBoyId + "" +
                session.device_token + ""
        )
        binding.progressBar.visibility = View.VISIBLE

        val hashMap: HashMap<String, String> = HashMap()
        hashMap["user_id"] = session.deliverBoyId

        RestClient.getInstance().apiInterface.assignedorderList(hashMap)
                .enqueue(object : Callback<OrderListResponse<AssignedOrder<ItemDetails>>> {
                    override fun onFailure(call: Call<OrderListResponse<AssignedOrder<ItemDetails>>>, t: Throwable) {
                        forceRefresh = false
                        isRefreshing = false
                        swipe_view.isRefreshing = false

                        binding.progressBar.visibility = View.GONE
                        context?.toast("Network error")
                        Log.d(TAG, "loadAssignedOrder onFailure ", t)
                    }

                    override fun onResponse(
                            call: Call<OrderListResponse<AssignedOrder<ItemDetails>>>,
                            response: Response<OrderListResponse<AssignedOrder<ItemDetails>>>
                    ) {
                        swipe_view.isRefreshing = false
                        forceRefresh = false
                        isRefreshing = false

                        binding.progressBar.visibility = View.GONE
                        if (response.isSuccessful && response.body()!!.status_code == 1) {
                            relEmptyWL.visibility = View.GONE
                            assignedOrderAdapter.assignedOrderList = response.body()!!.data!!
                            if (response.body()!!.data!!.isEmpty()){
                                relEmptyWL.visibility = View.VISIBLE
                            }

                        } else {
                            relEmptyWL.visibility = View.VISIBLE
                            context?.toast(response.body()?.message ?: "Opps something went wrong")
                        }
                    }
                })
    }
}