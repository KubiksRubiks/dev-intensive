package ru.skillbranch.devintensive.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.PorterDuff.Mode
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.Dimension.DP
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.utils.Utils
import java.lang.IllegalArgumentException
import kotlin.math.min
import kotlin.math.pow

class AvatarImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    private var avatarSize: Int = 0
    private var rect: Rect = Rect()
    private var pathR: Path = Path()
    private lateinit var paintText: Paint
    private lateinit var paintBorder: Paint
    private var borderWidth: Float = DEFAULT_BORDER_WIDTH
    private var borderColor: Int = DEFAULT_BORDER_COLOR
    private var initials: String? = null
    private val bgColors = arrayOf(
        "#7BC862",
        "#E17076",
        "#FAA774",
        "#6EC9CB",
        "#65AADD",
        "#A695E7",
        "#EE7AAE"
    )

    companion object {
        private const val DEFAULT_BORDER_WIDTH = 2F
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageView, defStyleAttr, 0)
        borderWidth = a.getDimensionPixelSize(
            R.styleable.AvatarImageView_aiv_borderWidth,
            DEFAULT_BORDER_WIDTH.toInt()
        ).toFloat()
        borderColor = a.getColor(R.styleable.AvatarImageView_aiv_borderColor, DEFAULT_BORDER_COLOR)
        a.recycle()

    }

    fun setInitials(initials: String) {

    }
}