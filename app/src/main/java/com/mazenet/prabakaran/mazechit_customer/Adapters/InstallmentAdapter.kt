package com.mazenet.prabakaran.mazechit_customer.Adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.prabakaran.mazechit_customer.Model.InstallmentsDet
import com.mazenet.prabakaran.mazechit_customer.R

class InstallmentAdapter(private val installmentList:ArrayList <InstallmentsDet>) : RecyclerView.Adapter<InstallmentAdapter.InstallmentViewHolder>() {

    class InstallmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtAuctionNo: TextView = view.findViewById(R.id.txtAuctionNo)
        val txtPending: TextView = view.findViewById(R.id.txtPending)
        val txtPenalty: TextView = view.findViewById(R.id.txtPenalty)
        val txtBonus: TextView = view.findViewById(R.id.txtBonus)
        val txtTotalPending: TextView = view.findViewById(R.id.txtTotalPending)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstallmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_installment_detail, parent, false)
        return InstallmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstallmentViewHolder, position: Int) {
        val installmentDetail = installmentList[position]
        Log.d("BindViewHolder","Binding data at position $position:$installmentDetail")
        holder.txtAuctionNo.text = installmentDetail.getAuctionNo()
        holder.txtPending.text = installmentDetail.getPendingDue()
        holder.txtPenalty.text = installmentDetail.getPenaltyAmounts()
        holder.txtBonus.text = installmentDetail.getBonusDays()

        val pendingDue = installmentDetail.getPendingDue()?.toDoubleOrNull() ?: 0.0
        val penaltyAmounts = installmentDetail.getPenaltyAmounts()?.toDoubleOrNull() ?: 0.0
        val totalPending = pendingDue + penaltyAmounts

        holder.txtTotalPending.text = totalPending.toString()
    }

    override fun getItemCount() = installmentList.size
}