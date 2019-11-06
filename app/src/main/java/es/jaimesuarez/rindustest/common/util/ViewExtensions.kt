package es.jaimesuarez.rindustest.common.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import es.jaimesuarez.rindustest.R

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun Fragment.showToast(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackbar(
    parentView: View, content: String, @ColorRes colorRes: Int = R.color.colorAccent
) {
    Snackbar.make(parentView, content, Snackbar.LENGTH_SHORT).apply {
        view.setBackgroundColor(ContextCompat.getColor(context, colorRes))
        show()
    }
}

var View.show: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }