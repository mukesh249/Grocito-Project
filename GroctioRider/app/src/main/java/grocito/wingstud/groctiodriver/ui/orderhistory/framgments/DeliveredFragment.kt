package grocito.wingstud.groctiodriver.ui.orderhistory.framgments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import grocito.wingstud.groctiodriver.R
import grocito.wingstud.groctiodriver.account.AccountManager
import grocito.wingstud.groctiodriver.rest.network.RestClient
import grocito.wingstud.groctiodriver.rest.requests.HistoryOrder
import grocito.wingstud.groctiodriver.rest.requests.OrderListResponse
import grocito.wingstud.groctiodriver.ui.orderhistory.OrderHistoryAdapter
import kotlinx.android.synthetic.main.fragment_delivered.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class DeliveredFragment : Fragment() {
    private val session by lazy { AccountManager.getUserAccount()!! }

    private val orderCompletedAdapter by lazy { OrderHistoryAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivered, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        order_history_rv.layoutManager = LinearLayoutManager(requireContext())
        order_history_rv.adapter = orderCompletedAdapter
        loadOrderHistory()
    }
    private fun loadOrderHistory() {
        val hashMap = HashMap<String ,String>()
        hashMap["user_id"] = session.deliverBoyId
        RestClient.getInstance().apiInterface.completed(hashMap).enqueue(object : Callback<OrderListResponse<HistoryOrder>> {
            override fun onFailure(call: Call<OrderListResponse<HistoryOrder>>, t: Throwable) {

            }

            override fun onResponse(call: Call<OrderListResponse<HistoryOrder>>, response: Response<OrderListResponse<HistoryOrder>>) {
                if (response.isSuccessful) {
                    val body = response.body()!!
                    if (body.status_code == 1) {
                        Log.d(TAG, "order list fetched")
                        orderCompletedAdapter.ordersList = body.data!!
                    } else {
                    }
                }
            }
        })
    }

    companion object {
        private const val TAG = "OrderDeliveredFragment"
    }

}
