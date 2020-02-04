package grocito.wingstud.groctiodriver.ui.orderhistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import grocito.wingstud.groctiodriver.R
import grocito.wingstud.groctiodriver.ui.orderhistory.framgments.DeliveredFragment
import grocito.wingstud.groctiodriver.ui.orderhistory.framgments.RejectedFragment
import kotlinx.android.synthetic.main.activity_order_history.*

class OrderHistory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        orderHistoryFragmentContainer.adapter = PagerViewAdapter(supportFragmentManager)

        deliveredOrders.setOnClickListener { orderHistoryFragmentContainer.currentItem = 0 }
        rejectedOrders.setOnClickListener { orderHistoryFragmentContainer.currentItem = 1 }

        back_iv.setOnClickListener { onBackPressed() }

        orderHistoryFragmentContainer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0){
                    deliveredOrders.setTextColor(resources.getColor(R.color.colorPrimaryDark))
                    deliveredOrders.background = resources.getDrawable(R.drawable.rounded_conner_white)
                    rejectedOrders.background = resources.getDrawable(R.drawable.rounded_conner_border)
                    rejectedOrders.setTextColor(resources.getColor(R.color.white))
                }
                if (position == 1){
                    deliveredOrders.setTextColor(resources.getColor(R.color.white))
                    rejectedOrders.setTextColor(resources.getColor(R.color.colorPrimaryDark))
                    deliveredOrders.background =resources.getDrawable(R.drawable.rounded_conner_border)
                    rejectedOrders.background = resources.getDrawable(R.drawable.rounded_conner_white)
                }
            }
        })
    }
}

class PagerViewAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(i: Int): Fragment {
        return when (i) {
            0 -> DeliveredFragment()
            1 -> RejectedFragment()
            else -> null
        }!!

    }

    override fun getCount(): Int {
        return 2;
    }

}
