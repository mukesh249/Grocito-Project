package grocito.wingstud.groctiodriver.ui.earning


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar

import grocito.wingstud.groctiodriver.R
import grocito.wingstud.groctiodriver.account.AccountManager
import grocito.wingstud.groctiodriver.databinding.FragmentEarningBinding
import grocito.wingstud.groctiodriver.response.DataEarning
import grocito.wingstud.groctiodriver.response.EarningResponse
import grocito.wingstud.groctiodriver.rest.network.RestClient
import kotlinx.android.synthetic.main.fragment_earning.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class EarningFragment : Fragment() {


    private val adapter by lazy { EarningAdapter() }
    lateinit var binding : FragmentEarningBinding
    private val TAG = "EarningActivity"

    private val session by lazy {
        AccountManager.getUserAccount()!!
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_earning, container, false)
        binding.earningRv.layoutManager = LinearLayoutManager(activity)
        binding.earningRv.adapter = adapter
        loadOrders()
        return binding.getRoot()

    }


    private fun loadOrders() {
        val hashMap: HashMap<String, String> = HashMap()
        hashMap["user_id"] = session.deliverBoyId

        RestClient.getInstance().apiInterface.earningDetails(hashMap).enqueue(object : Callback<EarningResponse<DataEarning>> {
            override fun onFailure(call: Call<EarningResponse<DataEarning>>, t: Throwable) {
//                progress_bar.visibility = View.GONE
                Snackbar.make(root, "Network Error", Snackbar.LENGTH_LONG).show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<EarningResponse<DataEarning>>, response: Response<EarningResponse<DataEarning>>) {
//                progress_bar.visibility = View.GONE
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body!!.status_code == 1) {
                        relEmptyWL.visibility = View.GONE
                        earning_rv.visibility = View.VISIBLE
                        // points_vg.visibility = VISIBLE
                        //formula.text = body.points
                        adapter.earningList = body.data
                    } else {
                        relEmptyWL.visibility = View.VISIBLE
                        earning_rv.visibility = View.GONE
//                        Snackbar.make(root, body.message ?: "Opps! Something went wrong.", Snackbar.LENGTH_LONG).show()
                    }
                }
            }

        })
    }


}
