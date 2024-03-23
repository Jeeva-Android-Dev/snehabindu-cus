package com.mazenet.prabakaran.mazechit_customer.Activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.lang.Exception

class RegisterActivity :BaseActivity() {
    lateinit var pb_reg: ProgressBar
    lateinit var edt_username: EditText
    lateinit var edt_mobileno: EditText
    lateinit var btn_register: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mazenet.prabakaran.mazechit_customer.R.layout.registerscreen)
        toast("Enter Registerd User-name & Mobile No. to Register")

        val i = intent
        pb_reg = findViewById<ProgressBar>(R.id.pb_register)
        edt_username = findViewById<EditText>(R.id.edt_username)
        edt_mobileno = findViewById<EditText>(R.id.edt_mobileno)

        btn_register = findViewById(R.id.btn_register)

        btn_register.setOnClickListener {
            val username = edt_username.text.toString()
            val mobileNo = edt_mobileno.text.toString()

            if (username.isNotEmpty() && mobileNo.isNotEmpty()) {
                // Show Confirmation dialog
                val dialogView = layoutInflater.inflate(R.layout.showotpdilog, null)
                val etOtp = dialogView.findViewById<EditText>(R.id.edt_otp)
                val btnSubmitOTP = dialogView.findViewById<Button>(R.id.btn_submitotp)


                val alertDialogBuilder = AlertDialog.Builder(this)
                    .setView(dialogView)
                    .setCancelable(false)

                val alertDialog = alertDialogBuilder.create()

                btnSubmitOTP.setOnClickListener {
                    val enteredOtp = etOtp.text.toString()
                   // val password = etPassword.text.toString()
                   // val confirmPassword = etConfirmPassword.text.toString()

                    if (enteredOtp == "1111") { // Replace with your actual OTP validation

                        alertDialog.dismiss()
                         val PasswordDialogview = layoutInflater.inflate(R.layout.fragment_pwd_confirm_pwd,null)
                         val etPassword = PasswordDialogview.findViewById<EditText>(R.id.new_pass1)
                         val etconfirmPassword = PasswordDialogview.findViewById<EditText>(R.id.conf_pass1)
                         val btnSubmitPassword = PasswordDialogview.findViewById<Button>(R.id.pass_submit)

                         val passwordAlerDialogBuilder = AlertDialog.Builder(this)
                             .setView(PasswordDialogview)
                             .setCancelable(false)
                         val passwordAlertDialog = passwordAlerDialogBuilder.create()
                        btnSubmitPassword.setOnClickListener {
                            val password = etPassword.text.toString()
                            val confirmPassword = etconfirmPassword.text.toString()

                            if (password == confirmPassword) {
                                // Passwords match, navigate to LoginActivity
                                val intent = Intent(this@RegisterActivity, login::class.java)
                                startActivity(intent)
                                passwordAlertDialog.dismiss()
                            } else {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Passwords do not match",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        passwordAlertDialog.show()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Invalid OTP", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                alertDialog.show()

            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please enter both username and mobile number",
                    Toast.LENGTH_SHORT
                ).show()
            }

            fun goTologin() {
                toast("Logged in successfully")
                setPrefsString(Constants.application_logged_in, "yes")
                setPrefsInt(Constants.First_time, 1)
                val intent: Intent
                intent = Intent(
                    this@RegisterActivity,
                    login::class.java
                )
                startActivity(intent)
                finish()
            }
        }
    }
}


