package com.mazenet.prabakaran.mazechit_customer.Fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.mazenet.prabakaran.mazechit_customer.Activities.HomeActivity
import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants

class AboutPage : BaseFragment() {

    lateinit var imageview1: ImageView
    lateinit var aboutdes: TextView
    lateinit var infoTxtCredits: TextView
    lateinit var whyinfo: TextView
    lateinit var knowmore: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about_certificate, container, false)

        (activity as HomeActivity).setActionBarTitle("About Us")
        imageview1 = view.findViewById(R.id.aboutcertificate) as ImageView
        aboutdes = view.findViewById(R.id.abouttv) as TextView
        infoTxtCredits = view.findViewById(R.id.infoTxtCredits) as TextView
        whyinfo  = view.findViewById(R.id.whyChooseTextView) as TextView
        //knowmore = view.findViewById(R.id.knowMoreTextView) as TextView

        val newLinkText = "Know More" // Replace with the new text you want to display
        val websiteLink = "https://www.snehabinduchits.com/" // Replace with the actual web link

        // Set the initial text
        infoTxtCredits.text = newLinkText

      // Set an OnClickListener to handle the click event
        infoTxtCredits.setOnClickListener {
            // Handle the click event, e.g., open a web link
            val uri = Uri.parse(websiteLink)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        return view
    }





}


