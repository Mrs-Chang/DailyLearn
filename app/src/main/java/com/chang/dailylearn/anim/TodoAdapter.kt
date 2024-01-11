package com.chang.dailylearn.anim

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chang.dailylearn.R


class TodoAdapter(private val data: MutableList<String>, private val listener: OnTodoDoneListener) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    fun removeTodo(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var todoText: TextView
        var doneButton: Button

        init {
            todoText = itemView.findViewById(R.id.todoText)
            doneButton = itemView.findViewById(R.id.doneButton)
            doneButton.setOnClickListener {
                listener.onTodoDone(adapterPosition)
            }
        }

        fun bind(todo: String?) {
            todoText.text = todo
        }
    }

    interface OnTodoDoneListener {
        fun onTodoDone(position: Int)
    }

}