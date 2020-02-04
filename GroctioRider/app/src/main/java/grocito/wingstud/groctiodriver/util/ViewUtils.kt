package grocito.wingstud.groctiodriver.util

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import grocito.wingstud.groctiodriver.App
import grocito.wingstud.groctiodriver.extensions.defaultSharedPreferences

fun Context.toast(message : String){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}

fun Context.setProfileImage(image : String,imageView: ImageView){
    Glide.with(this).load(App.get().defaultSharedPreferences.getString("ImagePath","")+image).into(imageView)
}
val  BaseURL : String ="https://www.grocito.com/"