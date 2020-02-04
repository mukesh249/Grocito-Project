package grocito.wingstud.groctiodriver.ui.profile


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import grocito.wingstud.groctiodriver.R
import grocito.wingstud.groctiodriver.account.AccountManager
import grocito.wingstud.groctiodriver.databinding.FragmentProfile2Binding
import grocito.wingstud.groctiodriver.rest.network.RestClient
import grocito.wingstud.groctiodriver.util.setProfileImage
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private val session by lazy { AccountManager.getUserAccount()!! }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding : FragmentProfile2Binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile2, container, false)

        context?.setProfileImage(session.profile_image,binding.avatarIv)
        binding.nameTv.text = session.username
        binding.mobTv.text = session.mobile
        binding.emailTv.text = session.email
        binding.employeeId.text = session.employeeNo
        return binding.root
    }



}
