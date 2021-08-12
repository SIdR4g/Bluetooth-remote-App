@file:Suppress("DEPRECATION")

package com.example.quadrapedrobot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var topAnimation: Animation
    lateinit var bottomAnimation: Animation
    lateinit var image: ImageView
    lateinit var wel: TextView
    lateinit var lets_start: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        topAnimation=AnimationUtils.loadAnimation(this,R.anim.topanimation)
        bottomAnimation=AnimationUtils.loadAnimation(this,R.anim.bottomanimation)

        image=findViewById(R.id.imageView)
        wel=findViewById(R.id.welcome)
        lets_start=findViewById(R.id.start)

        image.animation= topAnimation
        wel.animation= bottomAnimation
        lets_start.animation = bottomAnimation

    }


    fun connect(view: View) {
        val intent = Intent(this,ConnectBluetooth::class.java)
        startActivity(intent)
    }

}

