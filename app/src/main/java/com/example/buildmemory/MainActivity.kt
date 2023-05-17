package com.example.buildmemory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.buildmemory.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var list= Array<String>(12){""}
    private var listValue= Array<Int>(12){it}
    private var count = 0
    private var nivel=20
    private var isCountDownTimerRunning=false
  private val countDownTimer= object: CountDownTimer(60000,1000) {
    override fun onTick(millisUntilFinished: Long) {
        isCountDownTimerRunning=true
    }
      override fun onFinish() {
          setAdapter(Array<String>(12){""},true)
          isCountDownTimerRunning=false
      }

  }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title="Memory Building"
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
      setAdapter(Array<String>(12){""},false)

        binding.startGame.setOnClickListener {
            val sharedPreferences=getSharedPreferences("MyFile", MODE_PRIVATE)
            val editor =sharedPreferences.edit()
          list = Array(12){Random.nextInt(nivel).toString() }
            for (i in list.indices) {
                editor.putString("$i",list[i])
            }
           editor.apply()
          setAdapter(list,true)
          countDownTimer.start()
            listValue.shuffle()
            count=0
            binding.number.text=""

        }

        binding.propose.setOnClickListener {
            if (isCountDownTimerRunning) return@setOnClickListener

            val sharedPreferences = getSharedPreferences("MyFile", MODE_PRIVATE)
            val editor =sharedPreferences.edit()
            editor.putString("value",list[listValue[count % 12]])
            editor.apply()

            binding.number.text=list[listValue[count % 12]]
            count++
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.menu_item,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.easy -> nivel = 20
            R.id.medium-> nivel=100
            R.id.hard -> nivel=1000
        }
        return true
    }

  fun setAdapter(list:Array<String>,isGameOn:Boolean) {
    val adapter=MyAdapter(this,list, isGameOn )
    binding.recyclerView.layoutManager=GridLayoutManager(this,3)
    binding.recyclerView.adapter=adapter
  }
}