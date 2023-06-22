package hr.tvz.android.whatsappinsights.model

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

class BreakdownYearTextView: androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init(){
        this.textSize = 16f
        this.setTypeface(null, Typeface.BOLD)
        this.setPadding(0, 16, 0, 0)
    }
}