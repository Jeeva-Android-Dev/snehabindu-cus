package com.mazenet.prabakaran.mazechit_customer.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.mazenet.prabakaran.mazechit_customer.Activities.HomeActivity
import com.mazenet.prabakaran.mazechit_customer.Adapters.AdapterMyChits
import com.mazenet.prabakaran.mazechit_customer.Adapters.AdapterMyOutstanding
import com.mazenet.prabakaran.mazechit_customer.Adapters.IAdapterClickListener
import com.mazenet.prabakaran.mazechit_customer.Model.MyChitsModel
import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.Retrofit.ICallService
import com.mazenet.prabakaran.mazechit_customer.Retrofit.RetrofitBuilder
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants
import kotlinx.android.synthetic.main.fragment_outstanding_page.*
import retrofit2.Call
import retrofit2.Response

class MyOutstanding : BaseFragment() {
    var Grouplist = ArrayList<MyChitsModel>()
    lateinit var Recycler_groups: RecyclerView
    // have to change adapter
    lateinit var Myoutstandingadapter: AdapterMyOutstanding

    lateinit var txt_outstanding: TextView
    lateinit var pb_outstanding:ProgressBar
    lateinit var swiper_myoutstanding: SwipeRefreshLayout

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    // Inflate the layout for this fragment
    val view = inflater.inflate(R.layout.fragment_outstanding_page, container, false)
        (activity as HomeActivity).setActionBarTitle("My Outstanding")
    txt_outstanding = view.findViewById(R.id.outstandingtext) as TextView
     //   pb_outstanding.visibility = View.GONE

       // (activity as HomeActivity).setActionBarColor(R.color.red_dash)
        Recycler_groups = view.findViewById(R.id.reecycler_outstanding) as RecyclerView
        pb_outstanding = view.findViewById(R.id.pb_outstanding) as ProgressBar
        swiper_myoutstanding = view.findViewById(R.id.swiper_outstanding) as SwipeRefreshLayout
        Recycler_groups.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        Recycler_groups.layoutManager = mLayoutManager
//        Recycler_newchits.addItemDecoration(DividerItemDecoration(context,0))
        Recycler_groups.itemAnimator = DefaultItemAnimator()
        pb_outstanding.visibility = View.GONE
        get_mychits()
        swiper_myoutstanding.setOnRefreshListener {
            get_mychits()
        }

        return view
    }

    private fun get_mychits() {
        pb_outstanding.visibility = View.VISIBLE
        val checklogin: ICallService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("customer_id", getPrefsString(Constants.CUST_PID, ""))
        loginparameters.put("tenant_id", getPrefsString(Constants.TENANT_ID, ""))
        loginparameters.put("db",getPrefsString(Constants.DB,""))
        val RequestCall = checklogin.get_mychits(loginparameters)
        RequestCall.enqueue(object : retrofit2.Callback<ArrayList<MyChitsModel>> {
            override fun onFailure(call: Call<ArrayList<MyChitsModel>>, t: Throwable) {
                pb_outstanding.visibility = View.GONE
                swiper_myoutstanding.isRefreshing=false
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ArrayList<MyChitsModel>>, response: Response<ArrayList<MyChitsModel>>) {
                pb_outstanding.visibility = View.GONE
                swiper_outstanding.isRefreshing=false
                if (response.isSuccessful) {

                    when {
                        response.code().equals(200) -> {
                            val resultlist = response.body()
                            if (resultlist!!.size > 0) {
                                integrateList(resultlist)
                            } else {
                                toast("Still No Group details Available")
                            }
                        }
                        response.code().equals(401) -> toast("Server Error")
                        response.code().equals(500) -> toast("Internal server Error")
                    }
                } else {
                    when {
                        response.code().equals(401) -> toast(response.message())
                        response.code().equals(500) -> toast("Internal server Error")
                    }

                }

            }


        })
    }

    fun integrateList(leadslist: ArrayList<MyChitsModel>) {
        Grouplist.clear()
        Grouplist.addAll(leadslist)
        Myoutstandingadapter = AdapterMyOutstanding(Grouplist, object : IAdapterClickListener {
            override fun onPositionClicked(view: View, position: Int) {
                val bundle = Bundle()
                bundle.putString("enrolid", Grouplist.get(position).getEnrollmentId()!!)
                bundle.putString("groupname", Grouplist.get(position).getGroupName()!!)
                bundle.putString("ticketno", Grouplist.get(position).getTicketNo()!!)
                doFragmentTransactionWithBundle(Outstanding_TableDt(), "OutstandingTable", true, bundle)
                
            }

            override fun onLongClicked(position: Int) {
            }
        })
        Recycler_groups.adapter = Myoutstandingadapter
    }
}

