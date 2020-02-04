package grocito.wingstud.groctiodriver.ui.earning

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import grocito.wingstud.groctiodriver.R
import grocito.wingstud.groctiodriver.extensions.inflate
import grocito.wingstud.groctiodriver.response.DataEarning
import kotlinx.android.synthetic.main.item_earning.view.*

class EarningAdapter : RecyclerView.Adapter<EarningAdapter.ViewHolder>() {


    var earningList : List<DataEarning> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(parent.inflate(R.layout.item_earning))
    }

    override fun getItemCount(): Int {
        return earningList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataEarning = earningList[position]
        holder.itemView.username_tv.text = dataEarning.user_name
        holder.itemView.timestamp.text = dataEarning.delivery_date+","+dataEarning.delivery_time
        holder.itemView.order_id.text = dataEarning.order_id
        holder.itemView.user_address.text = dataEarning.user_address
        holder.itemView.restaurant_tv.text = dataEarning.seller_name
        holder.itemView.restaurant_address.text = dataEarning.seller_address
//        holder.itemView.restaurant_address.text = dataEarning.seller_address
    }

    class ViewHolder (view : View) :RecyclerView.ViewHolder(view)
}