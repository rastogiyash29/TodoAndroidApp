package com.example.todolistassignment.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistassignment.R
import com.example.todolistassignment.models.ToDo
import com.google.android.material.button.MaterialButton


class RecyclerViewAdapter(private val itemList: MutableList<ToDo>, private var context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var adapterCallback: AdapterCallback? = null

    init {
        adapterCallback = context as AdapterCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemTextView.text = item.todo
        holder.deleteBtn.setOnClickListener {
            adapterCallback?.onDeleteToDo(item)
        }
        holder.updateBtn?.setOnClickListener{
            adapterCallback?.onUpdateToDo(item)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<ToDo>) {
        itemList.clear()
        itemList.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTextView: TextView
        var deleteBtn:MaterialButton
        var updateBtn:MaterialButton
        init {
            deleteBtn=itemView.findViewById(R.id.deleteButton)
            updateBtn=itemView.findViewById(R.id.editButton)
            itemTextView = itemView.findViewById(R.id.task_text)
        }
    }

    //Interface to call Activity Methods
    interface AdapterCallback {
        fun onUpdateToDo(todo:ToDo)
        fun onDeleteToDo(todo:ToDo)
    }

}
