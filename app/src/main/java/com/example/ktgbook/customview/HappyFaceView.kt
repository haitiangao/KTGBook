package com.example.ktgbook.customview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.ktgbook.R


class HappyFaceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    private var happyFace: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.happyface);
    private var xPosition = 0f
    private var yPosition = 0f

    private val speed = 10.25f

    private var xSpeed = speed
    private var ySpeed = speed
    private val paint = Paint()

    init {
        happyFace = Bitmap.createScaledBitmap(happyFace,
            happyFace.getWidth() / 2,
            happyFace.getHeight() / 2,
            false
        )
    }

    override fun onDraw(canvas: Canvas) {
        // call the super method to keep any drawing from the parent side.
        super.onDraw(canvas)
        canvas.drawColor(0x00000000)

        if (xPosition > width - happyFace.getWidth() || xPosition < 0) xSpeed *= -1f
        if (yPosition > height - happyFace.getHeight() || yPosition < 0) ySpeed *= -1f

        xPosition += xSpeed
        yPosition += ySpeed


        canvas.drawBitmap(happyFace, xPosition, yPosition, paint)
        invalidate()
    }

}