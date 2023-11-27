package com.androchef.cameraxfacedetection.face_detection

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import com.androchef.cameraxfacedetection.camerax.GraphicOverlay
import com.google.mlkit.vision.face.Face
import kotlin.math.round

class FaceContourGraphic(
    overlay: GraphicOverlay,
    private val face: Face,
    private val imageRect: Rect
) : GraphicOverlay.Graphic(overlay) {

    private val facePositionPaint: Paint
    private val idPaint: Paint
    private val boxPaint: Paint

    init {
        val selectedColor = Color.rgb(235, 32, 32)

        facePositionPaint = Paint()
        facePositionPaint.color = selectedColor

        idPaint = Paint()
        idPaint.color = selectedColor

        boxPaint = Paint()
        boxPaint.color = selectedColor
        boxPaint.style = Paint.Style.STROKE
        boxPaint.strokeWidth = BOX_STROKE_WIDTH
        boxPaint.textSize = 60f
    }

    override fun draw(canvas: Canvas?) {
        val rect = calculateRect(
            imageRect.height().toFloat(),
            imageRect.width().toFloat(),
            face.boundingBox
        )
        if (rect.width() > 350) {
            face.rightEyeOpenProbability?.let {
                canvas?.drawText(
                    "${ round(it * 100f) }%",
                    rect.left,
                    rect.top - 20,
                    boxPaint
                )
            }

            face.leftEyeOpenProbability?.let {
                canvas?.drawText(
                    "${ round(it * 100f) }%",
                    rect.right - 130,
                    rect.top - 20,
                    boxPaint
                )
            }
        }

        canvas?.drawRect(rect, boxPaint)
    }

    companion object {
        private const val BOX_STROKE_WIDTH = 5.0f
    }

}