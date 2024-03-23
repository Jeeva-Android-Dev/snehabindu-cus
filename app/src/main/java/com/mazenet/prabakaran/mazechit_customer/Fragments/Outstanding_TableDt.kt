package com.mazenet.prabakaran.mazechit_customer.Fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.mazenet.prabakaran.mazechit_customer.Activities.HomeActivity
import com.mazenet.prabakaran.mazechit_customer.Adapters.AdapterMyOutstanding
import com.mazenet.prabakaran.mazechit_customer.Adapters.InstallmentAdapter
import com.mazenet.prabakaran.mazechit_customer.Model.GroupDetailsModel
import com.mazenet.prabakaran.mazechit_customer.Model.InstallmentsDet
import com.mazenet.prabakaran.mazechit_customer.Model.NewChitData
import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.Retrofit.ICallService
import com.mazenet.prabakaran.mazechit_customer.Retrofit.RetrofitBuilder
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants
import kotlinx.android.synthetic.main.fragment_outstanding_table4.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class Outstanding_TableDt : BaseFragment() {


    lateinit var installment_layout: LinearLayout
    lateinit var Recycler_instalments: RecyclerView
    lateinit var totaloutstandingamount: TextView

    var enrollid = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View
        view = inflater.inflate(R.layout.fragment_outstanding_table4, container, false)
        (activity as HomeActivity).setActionBarTitle("Outstanding Report")

        hideProgressDialog()
        //=-----------------
        Recycler_instalments = view.findViewById(R.id.Recycler_instalments) as RecyclerView
        installment_layout = view.findViewById(R.id.installment_layout) as LinearLayout
        totaloutstandingamount = view.findViewById(R.id.txt_total_outstanding) as TextView



        Recycler_instalments.setHasFixedSize(true)
        val mInstallmentLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        Recycler_instalments.layoutManager = mInstallmentLayoutManager
        Recycler_instalments.itemAnimator = DefaultItemAnimator()
        val emptyList = arrayListOf<InstallmentsDet>()
        val adapter = InstallmentAdapter(emptyList)
        Recycler_instalments.adapter= adapter
        enrollid = arguments!!.getString("enrolid").toString()

        //=----------------
        get_group_detail()
       return view
    }
    private fun get_group_detail() {
        showProgressDialog()
        var resultlist = java.util.ArrayList<GroupDetailsModel>()
        val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        println("ten ${getPrefsString(Constants.TENANT_ID, "")} enrlid $enrollid ")
        loginparameters.put("tenant_id", getPrefsString(Constants.TENANT_ID, ""))
        loginparameters.put("entrollment_id", enrollid)
        loginparameters.put("db",getPrefsString(Constants.DB,""))
        val LeadListRequest = getleadslist.get_individual_enrollment(loginparameters)
        LeadListRequest.enqueue(object : Callback<ArrayList<GroupDetailsModel>> {
            override fun onFailure(call: Call<ArrayList<GroupDetailsModel>>, t: Throwable) {
                hideProgressDialog()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ArrayList<GroupDetailsModel>>, response: Response<ArrayList<GroupDetailsModel>>
            ) {
                hideProgressDialog()
                when {
                    response.isSuccessful -> {
                        when {
                            response.code().equals(200) -> {
                                resultlist = response.body()!!
                                System.out.println("recycler ${response.body()}")
                                if (resultlist.size > 0) {

                                    for (i in resultlist.indices) {
                                        val installlist = response.body()!!.get(i).getInstallmentsDet()!!
                                        val Installmentadapter = InstallmentAdapter(installlist)
                                        Recycler_instalments.adapter = Installmentadapter

                                        val totalOutstandingAmount = response.body()!!.get(i).getPendingAmount()
                                        totaloutstandingamount.text= totalOutstandingAmount.toString()

                                    }

                                } else {
                                }
                            }
                        }
                    }
                }
            }
        })
    }



}




