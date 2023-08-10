package com.test.noteappkb.presentation

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.test.noteappkb.R
import com.test.noteappkb.core.data.NoteEntities
import java.lang.ref.WeakReference

class CustomDialog(
    contextRef: WeakReference<Context>,
    layoutResId: Int,
    onClick: ((NoteEntities) -> Unit)? = null
) {

    private val alertDialog: AlertDialog
    private val customView: View
    private var data: NoteEntities = NoteEntities()
    private val adapter: MyAdapter = MyAdapter()

    init {
        val context =
            contextRef.get() ?: throw IllegalArgumentException("Context reference is null")
        val inflater = LayoutInflater.from(context)
        customView = inflater.inflate(layoutResId, null)
        val builder = AlertDialog.Builder(context).setView(customView)
        alertDialog = builder.show()
        alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val btnNegative = alertDialog.findViewById<TextView>(R.id.tvCancel)
        val btnPositive = alertDialog.findViewById<TextView>(R.id.tvConfirm)
        val rvSelection = alertDialog.findViewById<RecyclerView>(R.id.rvSelect)
        val edtName = alertDialog.findViewById<TextInputEditText>(R.id.edt_acc_name)
        val edtTransaction = alertDialog.findViewById<TextInputEditText>(R.id.edt_acc_price)
        val llcSelection = alertDialog.findViewById<LinearLayoutCompat>(R.id.llcSelection)
        val tvSelected = alertDialog.findViewById<TextView>(R.id.tvSelected)

        rvSelection.adapter = adapter
        edtName.doAfterTextChanged {
            if (it.isNullOrEmpty()) {
                return@doAfterTextChanged
            }
            data = data.copy(name = it.toString())
        }
        edtTransaction.doAfterTextChanged {
            if (it.isNullOrEmpty()) {
                return@doAfterTextChanged
            }
            data = data.copy(total = it.toString().toLong())
        }
        llcSelection.setOnClickListener {
            rvSelection.isVisible = !rvSelection.isVisible
        }
        adapter.setOnClickListener {
            tvSelected.text = it
            rvSelection.isVisible = false
            data = data.copy(type = it)
        }

        btnPositive.setOnClickListener {
            dismiss()
            onClick?.invoke(data)
        }
        btnNegative.setOnClickListener {
            dismiss()
        }

    }

    fun show() {
        alertDialog.show()
    }

    fun dismiss() {
        alertDialog.dismiss()
    }

    inner class MyAdapter() :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        private val data = getData()
        private var onClick: ((String) -> Unit)? = null

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.tvSelected)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_selection, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val currentItem = data[position]
            holder.textView.text = currentItem
            holder.itemView.rootView.setOnClickListener {
                onClick?.invoke(currentItem)
            }
        }

        override fun getItemCount() = data.size

        fun setOnClickListener(listener: (String) -> Unit) {
            onClick = listener
        }
    }

    fun getData(): List<String> = listOf("Expense", "Income")
}