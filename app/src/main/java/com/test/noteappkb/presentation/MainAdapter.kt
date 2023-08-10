package com.test.noteappkb.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.noteappkb.R
import com.test.noteappkb.core.data.NoteEntities

class MainAdapter :
    RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    var data :MutableList<NoteEntities> = mutableListOf()
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvTotal: TextView = itemView.findViewById(R.id.tvTotal)
        val tvType: TextView = itemView.findViewById(R.id.tvType)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_recent_transaction, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvDate.text = currentItem.date
        holder.tvName.text = currentItem.name?.toCapitalized()
        holder.tvTotal.text = currentItem.total.toString().toCurrencyFormat()
        holder.tvType.text = currentItem.type
    }

    override fun getItemCount() = data.size
}