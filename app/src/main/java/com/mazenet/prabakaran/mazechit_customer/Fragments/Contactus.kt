package com.mazenet.prabakaran.mazechit_customer.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.prabakaran.mazechit_customer.Activities.HomeActivity
import com.mazenet.prabakaran.mazechit_customer.R

class Contactus : BaseFragment() {

    lateinit var whatsappTextView: TextView
    lateinit var whyinfo: TextView
    lateinit var knowmore: TextView

    @SuppressLint("MissingInflatedId", "QueryPermissionsNeeded")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contactus, container, false)

        (activity as HomeActivity).setActionBarTitle("Contact Us")
         whatsappTextView = view.findViewById(R.id.whatsappTextView) as TextView

        whatsappTextView.setOnClickListener {
            val phoneNumber = "+917090039507"
            val sendIntent = Intent("android.intent.action.MAIN",
            Uri.parse("https://wa.me/$phoneNumber"))

            sendIntent.setPackage("com.whatsapp")

            if (sendIntent.resolveActivity(context!!.packageManager) != null) {
                startActivity(sendIntent)
            } else {
                // Fallback: Open in browser
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$phoneNumber"))
                startActivity(browserIntent)
            }
        }
        return view
    }
    }
