package com.contest.gitactionapplication.widgets
/**
 * Created by Manish Kumar
 */
import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
class CustomView(context: Context,@Nullable  attrs: AttributeSet) : View(context,attrs) {

    /**
     * This is called when the view is attached to a window.  At this point it
     * has a Surface and will start drawing.  Note that this function is
     * guaranteed to be called before {@link #onDraw(android.graphics.Canvas)},
     * however it may be called any time before the first onDraw -- including
     * before or after {@link #onMeasure(int, int)}.
     *
     * @see #onDetachedFromWindow()
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onAnimationEnd() {
        super.onAnimationEnd()
    }

    override fun onAnimationStart() {
        super.onAnimationStart()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val parcelable = super.onSaveInstanceState()!!
        val bundle:Bundle = Bundle()
        // The vars you want to save - in this instance a string and a boolean
        val someText = "Manish save this text"
        val someBoolean = true
        val state: SavedState = SavedState(parcelable,someText,someBoolean)
        bundle.putParcelable(SavedState.STATE, state)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if(state is Bundle){
            val bundle:Bundle = state
            val customViewState : SavedState = bundle.getParcelable<SavedState>(SavedState.STATE)!!
            // The vars you saved - do whatever you want with them
            val someText = customViewState.someText
            val somethingShowing = customViewState.somethingShowing
            super.onRestoreInstanceState(customViewState.superState)
        }
        // Stops a bug with the wrong state being passed to the super
        super.onRestoreInstanceState(BaseSavedState.EMPTY_STATE)
    }

    class SavedState : BaseSavedState {
        var someText: String? = null
        var somethingShowing = false

        constructor(persistIn: Parcel):super(persistIn){
            someText = persistIn.readString()
            somethingShowing= persistIn.readBoolean()
        }
        constructor(superState: Parcelable,num1:String,num2:Boolean):super(superState){
            this.someText = num1
            this.somethingShowing = num2
        }
        override fun writeToParcel(destination: Parcel?, flags: Int) {
            super.writeToParcel(destination, flags)
            destination?.writeString(someText);
            destination?.writeBoolean(somethingShowing);
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            val STATE = "YourCustomView.STATE"

            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }

    }
}