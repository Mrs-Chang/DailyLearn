package com.chang.dailylearn.anim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.chang.dailylearn.R
import com.chang.dailylearn.databinding.ActivityTodoBinding
import com.chang.dailylearn.view.immerse

class TodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoBinding
    private val todos = mutableListOf<String>()
    private lateinit var adapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        immerse()
        adapter = TodoAdapter(todos, object : TodoAdapter.OnTodoDoneListener {
            override fun onTodoDone(position: Int) {
                val slideOut = AnimationUtils.loadAnimation(this@TodoActivity, R.anim.slide_to_right)
                slideOut.setAnimationListener(object: Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                        todos.removeAt(position)
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        adapter.notifyItemRemoved(position)
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }

                })
                binding.recyclerView.getChildAt(position).startAnimation(slideOut)
            }
        })

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        binding.recyclerView.itemAnimator = TodoItemAnimator()

        binding.addButton.setOnClickListener {
            todos.add("Todo ${todos.size + 1}")
            adapter.notifyItemInserted(todos.size - 1)
            //binding.recyclerView.scheduleLayoutAnimation()
        }
    }
}