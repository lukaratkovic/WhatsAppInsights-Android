package hr.tvz.android.whatsappinsights.model

import android.content.Context
import android.util.AttributeSet

class TrendsMonthTextView: androidx.appcompat.widget.AppCompatTextView {
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
        this.setPadding(32, 0, 0, 0)
    }
}