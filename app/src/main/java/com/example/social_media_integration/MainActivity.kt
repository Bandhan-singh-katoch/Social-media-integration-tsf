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

        createUI()

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

            if(auth.photoUrl!=null) {      // if null shows default image else network image
                Glide.with(this)
                    .load(auth.photoUrl)
                    .fitCenter()
                    .into(binding.profileImage)
            }
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

    // on clicking back we come out of the app instead of login activity
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}