package grocito.wingstud.groctiodriver.ui.home

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import grocito.wingstud.groctiodriver.R
import grocito.wingstud.groctiodriver.extensions.inflate
import grocito.wingstud.groctiodriver.response.AssignedOrder
import grocito.wingstud.groctiodriver.response.ItemDetails
import kotlinx.android.synthetic.main.assigned_order_item.view.*

class AssignedOrderAdapter : RecyclerView.Adapter<AssignedOrderAdapter.ViewHolder>() {

    var trackListener: OrderTrackListener? = null

    var assignedOrderList : List<AssignedOrder<ItemDetails>> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.assigned_order_item))
    }

    override fun getItemCount(): Int {
        return assignedOrderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = assignedOrderList[position]
        holder.itemView.let {
            Log.d("Adapter", "Assigned Order $order")
//            val restaurantName = order.restaurantAddress
//            Log.d("Adapter", "name  " + restaurantName)
            it.restaurant_tv.text = order.seller_name
            it.restaurant_address.text = order.seller_address
            it.timestamp.text = order.delivery_date+" "+order.delivery_time
            it.order_id.text = "${order.order_id}"
//            it.amount_tv.text = order.amount
            it.payment_type.text = order.payment_mode
            it.username_tv.text = order.user_name
            it.user_address.text = order.user_address
        }
        holder.itemView.view_details_tv.setOnClickListener {
            trackListener?.onOrderDetail(order)
        }
        holder.itemView.restaurant_track.setOnClickListener {
            trackListener?.onSellerTrack(order)
        }
        holder.itemView.user_address_track.setOnClickListener {
            trackListener?.onUserTrack(order)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OrderTrackListener {
        fun onSellerTrack(assignedOrder: AssignedOrder<ItemDetails>)
        fun onUserTrack(assignedOrder: AssignedOrder<ItemDetails>)
        fun onOrderDetail(assignedOrder: AssignedOrder<ItemDetails>)
    }
}