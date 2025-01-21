package com.example.lab03game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), GameTask {
    lateinit var rootLayout :LinearLayout
    lateinit var startBtn : Button
    lateinit var mGameView: GameView
    lateinit var score:TextView
    lateinit var name:TextView
    lateinit var background:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        name = findViewById(R.id.name)
        background = findViewById(R.id.background)
        mGameView = GameView(this,this)

        startBtn.setOnClickListener {
            mGameView.setBackgroundResource(R.drawable.wall)
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
            score.visibility = View.GONE
            background.visibility = View.GONE
            name.visibility = View.GONE
        }
    }

    override fun closeGame(Score: Int) {

        score.text = "Score : $Score"
        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
        background.visibility = View.VISIBLE
        //Reset variables for a new game session
        mGameView = GameView(this, this)

    }
}