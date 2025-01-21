package com.example.lab03game

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameView(var c: Context, var gameTask: GameTask) : View(c) {
    private var myPaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var ninjaPosition = 0
    private val bomb = ArrayList<HashMap<String, Any>>()

    var viewWidth = 0
    var viewHeight = 0

    init {
        myPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        //add bombs to map
        if (time % 700 < 10 + speed) {
            val map = HashMap<String, Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            bomb.add(map)
        }
        time += 10 + speed

        //calculate the width and height of the ninja based on the demention of game view
        val ninjaWidth = viewWidth / 5
        val ninjaHeight = ninjaWidth + 10
        myPaint!!.style = Paint.Style.FILL

        //draw ninja into canvas
        val ninjaDrawable = resources.getDrawable(R.drawable.minininja, null)
        ninjaDrawable.setBounds(
            ninjaPosition * viewWidth / 3 + viewWidth / 15 + 25,
            viewHeight - 2 - ninjaHeight,
            ninjaPosition * viewWidth / 3 + viewWidth / 15 + ninjaWidth - 25,
            viewHeight - 2
        )
        ninjaDrawable.draw(canvas)

        // Draw bombs to canvas
        myPaint!!.color = Color.YELLOW
        val iterator = bomb.iterator()
        while (iterator.hasNext()) {
            val ninja = iterator.next()
            val lane = ninja["lane"] as Int
            val ninjaX = lane * viewWidth / 3 + viewWidth / 15
            var ninjaY = time - ninja["startTime"] as Int


            val bombDrawable = resources.getDrawable(R.drawable.bomb, null)
            bombDrawable.setBounds(
                ninjaX + 25,
                ninjaY - ninjaHeight,
                ninjaX + ninjaWidth - 25,
                ninjaY
            )
            bombDrawable.draw(canvas)
            //close the game
            if (lane == ninjaPosition && ninjaY > viewHeight - 2 - ninjaHeight && ninjaY < viewHeight - 2) {
                gameTask.closeGame(score)
            }

            if (ninjaY > viewHeight + ninjaHeight) {
                iterator.remove()
                score++
                speed = 1 + score / 8
            }
        }


        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f
        canvas.drawText("Score : $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed : $speed", 380f, 80f, myPaint!!)
        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                //check touch on left side
                if (x1 < viewWidth / 2) {
                    if (ninjaPosition > 0) {
                        ninjaPosition--
                    }
                }
                //check touch on right side
                if (x1 > viewWidth / 2) {
                    if (ninjaPosition < 2) {
                        ninjaPosition++
                    }
                }
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }
}
