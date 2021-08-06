package com.example.social_media_integration

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.social_media_integration.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val auth = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        if(auth!=null && intent!=null){
            createUI()
        }
        else{
            startActivity(Intent(this,LoginActivity::class.java))
            this.finish()
        }

        binding.logoutBtn.setOnClickListener {
            AuthUI.getInstance().signOut(this).addOnSuccessListener {
                Toast.makeText(this,"Logged out successfully",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,LoginActivity::class.java))
            }
        }

    }

    private fun createUI(){

        auth?.let {
            binding.txtName.text = auth.displayName
            binding.txtemail.text = auth.email
            binding.txtPhoneNumber.text = auth.phoneNumber

            Glide.with(this)
                .load(auth.photoUrl)
                .fitCenter()
                //.placeholderDrawable(R.drawable.profile_img)
                .into(binding.profileImage)
        }

    }

    override fun onResume() {
        super.onResume()

        if(auth!=null && intent!=null){
            createUI()
        }
        else{
            startActivity(Intent(this,LoginActivity::class.java))
            this.finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }


    //generateFBKey()  // in oncreate
    //    fun generateFBKey(){
//        try {
//            val info = packageManager.getPackageInfo(
//                "com.example.social_media_integration",
//                PackageManager.GET_SIGNATURES
//            )
//            for (signature in info.signatures) {
//                val md: MessageDigest = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
//            }
//        } catch (e: PackageManager.NameNotFoundException) {
//        } catch (e: NoSuchAlgorithmException) {
//        }
//    }
}