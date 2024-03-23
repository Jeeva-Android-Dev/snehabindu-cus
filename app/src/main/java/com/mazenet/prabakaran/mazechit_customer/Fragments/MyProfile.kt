package com.mazenet.prabakaran.mazechit_customer.Fragments

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.mazenet.prabakaran.mazechit_customer.Activities.HomeActivity
import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants
import java.io.IOException
import com.github.barteksc.pdfviewer.PDFView


class MyProfile : BaseFragment() {
    lateinit var profile_username: TextView
    lateinit var profile_mobileno: TextView

    lateinit var btnadhar: Button
    lateinit var cus_pan: Button
    lateinit var cus_bank: Button
    lateinit var cus_income: Button
    lateinit var cus_pic : Button
    lateinit var cus_location :Button
    lateinit var cus_submit : Button
    lateinit var  imageUri : Uri

    lateinit var imageview1: ImageView
    lateinit var imageview2: ImageView
    lateinit var imageview3: ImageView
    lateinit var imageview4: ImageView
    lateinit var imageview5: ImageView
    lateinit var imageview6: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        (activity as HomeActivity).setActionBarTitle("My Profile")
        btnadhar = view.findViewById(R.id.cus_adharfront) as Button
        cus_bank = view.findViewById(R.id.cus_bank)  as Button
        cus_pan = view.findViewById(R.id.pan_btn) as Button
        cus_income = view.findViewById(R.id.income_btn) as Button
        cus_pic = view.findViewById(R.id.cus_picbtn) as Button
        cus_location = view.findViewById(R.id.location_btn) as Button
        cus_submit = view.findViewById(R.id.mediasubmit) as Button

        imageview1 = view.findViewById(R.id.adharimg) as ImageView
        imageview2 = view.findViewById(R.id.Bankimg) as ImageView
        imageview3 = view.findViewById(R.id.panimg) as ImageView
        imageview4 = view.findViewById(R.id.incomeimg) as ImageView
        imageview5 = view.findViewById(R.id.custimg) as ImageView
        imageview6 = view.findViewById(R.id.locationimg) as ImageView

        btnadhar.setOnClickListener(){
           // showPictureDialog()
            showImagePickerDialog(IMAGE_REQUEST_1)
        }
        cus_bank.setOnClickListener(){
            showImagePickerDialog(IMAGE_REQUEST_2)
        }
        cus_pan.setOnClickListener(){
            showImagePickerDialog(IMAGE_REQUEST_3)

        }
        cus_income.setOnClickListener(){
            showImagePickerDialog(IMAGE_REQUEST_4)
        }
        cus_pic.setOnClickListener(){
           showImagePickerDialog(IMAGE_REQUEST_5)
        }
        cus_location.setOnClickListener(){
            showImagePickerDialog(IMAGE_REQUEST_6)

        }
        profile_username= view.findViewById(R.id.txt_username1)
        profile_username.text = getPrefsString(Constants.CUST_NAME, "")
        profile_mobileno = view.findViewById(R.id.txt_user_mobile)
        profile_mobileno.text = getPrefsString(Constants.TENANT_MOBILENO, "")
       // profile_mobileno.text ="9916465143"



        cus_submit.setOnClickListener(){
            toast("Submitted Successfully")
        }
        return view
    }
    private fun showImagePickerDialog(requestCode: Int) {
        val items = arrayOf("Camera", "Gallery","PDF")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose an option")
            .setItems(items) { _, which ->
                when (which) {
                    0 -> checkCameraPermission(requestCode)
                    1 -> openGallery(requestCode)
                    2 -> openPdfPicker(requestCode)

                }
            }
        builder.create().show()
    }
    private fun checkCameraPermission(requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera(requestCode)
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST
            )
        }
    }


    private fun openCamera(requestCode: Int) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, requestCode)
    }

    private fun openGallery(requestCode: Int) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, requestCode)
    }

    private fun openPdfPicker(requestCode: Int) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/pdf"
        try{
            startActivityForResult(intent,requestCode)
        } catch (e:ActivityNotFoundException){
            showMessage("No app availble to open PDF files")
        }
    }
    private fun showMessage(message: String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val imageView = when (requestCode) {
                IMAGE_REQUEST_1 -> imageview1
                IMAGE_REQUEST_2 -> imageview2
                IMAGE_REQUEST_3 -> imageview3
                IMAGE_REQUEST_4 -> imageview4
                IMAGE_REQUEST_5 -> imageview5
                IMAGE_REQUEST_6 -> imageview6
                else -> null
            }

            imageView?.let {
                if (requestCode == CAMERA_REQUEST) {
                    // Camera result
                    data.extras?.get("data")?.let { image ->
                        if (image is Bitmap) {
                            updateImageView(it, data)
                        }
                    }
                } else {
                    // Gallery result
                    updateImageView(it, data)
                }
            }
        }
    }

  private fun updateImageView(imageView: ImageView, data: Intent) {
        when (imageView) {
            imageview1, imageview2, imageview3, imageview4, imageview5 -> {
                data.data?.let { uri ->
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                    imageView.setImageBitmap(bitmap)
                }
            }
        }
    }
    companion object {
        private const val IMAGE_REQUEST_1 = 1
        private const val IMAGE_REQUEST_2 = 2
        private const val IMAGE_REQUEST_3 = 3
        private const val IMAGE_REQUEST_4 = 4
        private const val IMAGE_REQUEST_5 = 5
        private const val IMAGE_REQUEST_6 = 6
        private const val CAMERA_REQUEST = 999

    }
}
