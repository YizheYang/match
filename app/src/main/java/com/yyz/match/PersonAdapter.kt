package com.yyz.match

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yyz.match.entity.PersonBean

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.10 下午 4:34
 * version 1.0
 * update none
 **/
class PersonAdapter(private val list: MutableList<PersonBean>) :
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    private var onClickListener: OnClickListener? = null

    inner class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_item)
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        val holder = PersonViewHolder(view)
        holder.itemView.setOnClickListener {
            onClickListener?.onItemClick(holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val sb = StringBuilder()
        sb.append(position + 1).append(".").append(list[position].parameter)
        holder.title.text = sb.toString()
    }

    override fun getItemCount() = list.size
}