package com.example.bmi

import android.app.Activity
import android.content.Context
import android.graphics.Color.blue
import android.icu.text.DecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val v = currentFocus
        if (v != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE)
            && v is EditText
            && !v.javaClass.name.startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            v.getLocationOnScreen(scrcoords)
            val x = ev.rawX + v.getLeft() - scrcoords[0]
            val y = ev.rawY + v.getTop() - scrcoords[1]
            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()
            ) hideKeyboard(this)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun hideKeyboard(activity: Activity?) {
        if (activity != null && activity.window != null && activity.window.decorView != null
        ) {
            val imm = activity
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                activity.window.decorView
                    .windowToken, 0
            )
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        val button = findViewById(R.id.button) as Button
        val foot = findViewById(R.id.foot) as EditText
        val inch = findViewById(R.id.inch) as EditText
        val kgs = findViewById(R.id.kgs) as TextView
        val result = findViewById(R.id.result) as TextView
        val footHeading = findViewById(R.id.footsHeading) as TextView
        val inchHeading = findViewById(R.id.inchHeading) as TextView
        val kgHeading = findViewById(R.id.kgHeading) as TextView
        val heightHeading = findViewById(R.id.heightHeading) as TextView
        val weightHeading = findViewById(R.id.weightHeading) as TextView
        val gcb = findViewById(R.id.gcb) as TextView

        fun animation() {
            gcb.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        }

        val shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        gcb.startAnimation(shake);

        gcb.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                animation()
                //button2.performClick()
                handler.postDelayed(this, 1000)//1 sec delay
            }
        }, 0)



        button.setOnClickListener {

            if (foot.text.trim().length == 0) {

                footHeading.setTextColor(getResources().getColor(R.color.red))
                findViewById<TextView>(R.id.result).apply { text = "Enter Foots" }

            }
            else {
                footHeading.setTextColor(getResources().getColor(R.color.black))
            }

            if (inch.text.trim().length == 0) {

                findViewById<TextView>(R.id.result).apply { text = "Enter inches (Enter 0 for blank)" }
                inchHeading.setTextColor(getResources().getColor(R.color.red))

            }
            else {
                inchHeading.setTextColor(getResources().getColor(R.color.black))
            }

            if (kgs.text.trim().length == 0) {

                findViewById<TextView>(R.id.result).apply { text = "Enter Weight in Kilograms" }
                kgHeading.setTextColor(getResources().getColor(R.color.red))

            }
            else {
                kgHeading.setTextColor(getResources().getColor(R.color.black))
            }

            if (foot.text.trim().length > 0 && inch.text.trim().length > 0 && kgs.text.trim().length > 0) {

                val num1 = foot.text.toString().toInt()
                val num2 = inch.text.toString().toInt()
                val user3 = kgs.text.toString().toFloat()
                val num3: Float = user3
                val weightA = (num1 * 12) + num2  // Total Inches
                val weightB = weightA / 39.37 // Converting To Meteres
                val weightC = weightB * weightB // taking square of Meters
                val formula = num3 / weightC // formula

                if (formula < 19) {
                    var weightE = 19 * weightC
                    var weightF = weightE - num3

                    findViewById<TextView>(R.id.result).apply {
                        text =
                            "Your BMI = ${formula.toInt()}\nYou are Underweight\nYou must gain atleast ${weightF.toInt()} kgs to become fit"
                    }
                    result.setTextColor(getResources().getColor(R.color.blue))

                } else if (formula >= 19.1 && formula <= 24) {

                    findViewById<TextView>(R.id.result).apply {
                        text = "Your BMI = ${formula.toInt()}\nYou are fit and Healthy"
                    }
                    result.setTextColor(getResources().getColor(R.color.green))

                } else if (formula >= 24.1 && formula <= 29) {

                    var weightE = 24 * weightC
                    var weightF = num3 - weightE

                    findViewById<TextView>(R.id.result).apply {
                        text =
                            "Your BMI = ${formula.toInt()}\nYou are over weight\nYou must reduce atleast ${weightF.toInt()} kgs to become fit"
                    }
                    result.setTextColor(getResources().getColor(R.color.orange))


                } else if (formula >= 29.1 && formula <= 39) {

                    var weightE = 24 * weightC
                    var weightF = num3 - weightE

                    findViewById<TextView>(R.id.result).apply {
                        text =
                            "Your BMI = ${formula.toInt()}\nYou are Obese\nYou must reduce atleast ${weightF.toInt()} kgs to become fit"
                    }
                    result.setTextColor(getResources().getColor(R.color.Dred))


                } else {

                    var weightE = 24 * weightC
                    var weightF = num3 - weightE

                    findViewById<TextView>(R.id.result).apply {
                        text =
                            "Your BMI = ${formula.toInt()}\nYou are Extreemely Obese\nYou must reduce atleast ${weightF.toInt()} kgs to become fit"
                    }
                    result.setTextColor(getResources().getColor(R.color.red))


                }
            }



























            else
            {
                Toast.makeText(this, "Enter All Values (enter 0 for blank fields)", Toast.LENGTH_LONG).show()
            }

            kgs.setOnKeyListener { _, keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                    button.performClick()


                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

        }











    }
}