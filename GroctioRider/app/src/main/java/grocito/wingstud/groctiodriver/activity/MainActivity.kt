package grocito.wingstud.groctiodriver.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.gms.common.util.CollectionUtils.setOf
import grocito.wingstud.groctiodriver.App
import grocito.wingstud.groctiodriver.R
import grocito.wingstud.groctiodriver.account.AccountManager
import grocito.wingstud.groctiodriver.ui.earning.EarningFragment
import grocito.wingstud.groctiodriver.ui.home.HomeFragment
import grocito.wingstud.groctiodriver.ui.orderhistory.OrderHistory
import grocito.wingstud.groctiodriver.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val session by lazy { AccountManager.getUserAccount()!! }

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val view : View = nav_view.getHeaderView(0)
        view.txtUsername.text = session.username
        view.emailTV.text = session.employeeNo
        Glide.with(this)
                .load( App.get().defaultSharedPreferences.getString("ImagePath","")+session.profile_image)
                .centerCrop()
                .into(view.imvUserImage)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_profile, R.id.nav_earning,
                R.id.nav_tools, R.id.nav_slideshow, R.id.nav_history), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        nav_view.setNavigationItemSelectedListener { menuItem ->
            val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    if (currentFragment !is ProfileFragment) {
                        toolbar.title = "Profile"
                        supportFragmentManager
                                .beginTransaction()
                                .add(R.id.nav_host_fragment, ProfileFragment()).addToBackStack(null)
                                .commit()
                    }
                }
                R.id.nav_home ->{
                    if (currentFragment !is HomeFragment) {
                        toolbar.title = "Home"
                        supportFragmentManager
                                .beginTransaction()
                                .add(R.id.nav_host_fragment, HomeFragment()).addToBackStack(null)
                                .commit()
                    }
                }
                R.id.nav_earning ->{
                    if (currentFragment !is EarningFragment) {
                        toolbar.title = "Your Earning"
                        supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, EarningFragment()).addToBackStack(null)
                                .commit()
                    }
                }
                R.id.nav_history ->{
                    startActivity(Intent(this,OrderHistory::class.java))
                }
                R.id.nav_logout -> {
                    menuItem.isChecked = true
                    alert("Are you sure you want to logout?", null) {
                        yesButton {
                            it.dismiss()
                            App.get().logout()
                            finish()
                        }
                        noButton { it.dismiss() }
                    }.show()
                }
            }
            drawer_layout.closeDrawer(Gravity.START)
            true
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
