package grocito.wingstud.groctiodriver.ui.orderhistory.framgments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import grocito.wingstud.groctiodriver.R


/**
 * A simple [Fragment] subclass.
 */
class RejectedFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rejected, container, false)
    }


}
