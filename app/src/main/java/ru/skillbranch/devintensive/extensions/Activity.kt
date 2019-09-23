package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager


/** * Hides the soft keyboard  */
fun Activity.hideKeyboard() {
    val focus = this.currentFocus
    focus?.let {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let {
            it.hideSoftInputFromWindow(focus.windowToken, 0)
        }
    }
}

fun Activity.getRootView(): View {
    return findViewById(android.R.id.content)
}

fun Activity.isKeyboardOpen(): Boolean {
    val rootView: View = this.getRootView()
    rootView.getWindowVisibleDisplayFrame(Rect())
    val screenHeight = getRootView().height
    val heightDiff = getRootView().height - (Rect().bottom - Rect().top)

    return heightDiff > screenHeight / 3
}

fun Activity.isKeyboardClosed():Boolean{
    return this.isKeyboardOpen().not()
}