package com.sixD.leaderboard.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * Glide transformation to create rounded corner images
 */
class RoundedCornersTransformation(private val radius: Float) : BitmapTransformation() {

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        return roundCorners(pool, toTransform, radius)
    }

    private fun roundCorners(pool: BitmapPool, source: Bitmap, radius: Float): Bitmap {
        val result = pool.get(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)

        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = -0x1000000 // Black color

        val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rectF, radius, radius, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(source, null, Rect(0, 0, source.width, source.height), paint)

        return result
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update("RoundedCornersTransformation$radius".toByteArray())
    }
}
