package com.example.buildmemory

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode

class MyAdapter(private val context: Context, private val list: Array<String>,private val isGameOn:Boolean):RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    var count=0
    var score = 0

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.model_item, parent, false)
        )
    }

    override fun getItemCount() = 12


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sharedPreferences = context.getSharedPreferences("MyFile",AppCompatActivity.MODE_PRIVATE)
        holder.textView.text = list[position]
        holder.textView.setOnClickListener {
            score++
            if (!isGameOn || holder.textView.text.toString().isNotEmpty()) {
                return@setOnClickListener
            }

            else if (sharedPreferences.getString("$position","Ok")==
                    sharedPreferences.getString("value","Ok")) {
                    holder.textView.text=sharedPreferences.getString("value","Ok")
                holder.textView.setTextColor(ContextCompat.getColor(context,R.color.white))
                count++
                }
            else {
                Toast.makeText(context,
                    "It is not a match",Toast.LENGTH_SHORT).show()
            }
            if (count==12) {
                val builder=AlertDialog.Builder(context)
                val mean=(12.0/score.toDouble()).toBigDecimal().setScale(2,RoundingMode.UP).toDouble()

                with(builder) {
                    setTitle("Congratulation")
                    setMessage("You score is $mean")
                    setPositiveButton("Ok") {_,_,->
                    }
                    show()
                }

            }




        }

    }

}
