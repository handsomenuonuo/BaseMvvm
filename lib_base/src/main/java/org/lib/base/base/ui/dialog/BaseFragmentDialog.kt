package org.lib.base.base.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.annotation.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import org.lib.base.R
import org.lib.base.utils.trueLet

/**********************************
 * @Name:         BaseDialog
 * @Copyright：  CreYond
 * @CreateDate： 2021/4/29 15:38
 * @author:      HuangFeng
 * @Version：    1.0
 * @Describe:
 *
 **********************************/
class BaseFragmentDialog : DialogFragment() {
    private var fm: FragmentManager? = null
    private var t : String = "dialog"
    private var layout :Int = 0
    private var anim : Int = 0
    private var perSent : Float = 1.0f
    private var gravity : Int = 0
    private var cancelOutside : Boolean = true
    private var c: Boolean = true
    private var clickViewMap : HashMap<Int, Dialog.(View)->Unit> = HashMap()
    private var getViewMap : HashMap<Int, View.(View)->Unit> = HashMap()
    private var rootViewFun: Dialog.(View) -> Unit ?= {}
    private var textMap: HashMap<Int, String> = HashMap()
    private var backgroundAlpha: Float = 0.4f
    private var  cancelId: Int = 0
    private var  mHeight: Int = 0
    private var  mWidth: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.let {
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setCanceledOnTouchOutside(cancelOutside)
        }
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setGravity(gravity)
            it.setWindowAnimations(anim)
        }

        isCancelable = c

        var view =  inflater.inflate(layout, container, false)
        (cancelId !=0).trueLet {
            view.findViewById<View>(cancelId).setOnClickListener{
                dialog?.cancel()
            }
        }
        for(key in clickViewMap.keys){
            view.findViewById<View>(key).setOnClickListener{
                clickViewMap[key]?.invoke(dialog!!,view)
            }
        }
        for(key in getViewMap.keys){
            view.findViewById<View>(key).apply {
                getViewMap[key]?.invoke(this,view)
            }
        }
        for(key in textMap.keys){
            (view.findViewById<View>(key) as TextView).text = textMap[key]
        }
        rootViewFun.invoke(dialog!!,view)
        return view
    }


    override fun onStart() {
        super.onStart()
        val dm = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getRealMetrics(dm)
        var w = if( mWidth == 0){
            (dm.widthPixels*perSent).toInt()
        }else{
            resources.getDimension(mWidth).toInt()
        }
        var h =  if(mHeight == 0){
            ViewGroup.LayoutParams.WRAP_CONTENT
        }else{
            resources.getDimension(mHeight).toInt()
        }
        dialog?.window?.setLayout(w,h)

        dialog?.window?.attributes?.let {
            it.dimAmount = backgroundAlpha
            it.flags = it.flags.or(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            dialog?.window?.attributes =it
        }
    }


    /***
     * 展示弹框，如果layout不传，会在屏幕中间，弹出默认弹框
     */
    fun show(){
        show(fm!!,t)
    }

    class Builder(fm: FragmentManager) {
        private var fragmentManager: FragmentManager ?= fm
        @LayoutRes
        private var layout: Int = 0
        @StyleRes
        private var anim: Int = 0
        private var perSent: Float = 0.85f
        private var cancelOutside: Boolean = true
        private var height = 0
        private var width = 0
        private var clickViewMap: HashMap<Int, Dialog.(View) -> Unit> = HashMap()
        private var getViewMap: HashMap<Int, View.(View) -> Unit> = HashMap()
        private var rootViewFun: Dialog.(View) -> Unit ?= {}
        private var textMap: HashMap<Int, String> = HashMap()
        private var cancelable: Boolean = true
        private var backgroundAlpha: Float = 0.4f
        @IdRes
        private var  cancelId: Int = 0
        @DialogGravity.BaseGravity
        private var gravity: Int = Gravity.CENTER
        private var tag : String = "dialog"

        /***
         * 设置自定义layout
         */
        fun layout(@LayoutRes layout: Int): Builder {
            this.layout = layout
            return this
        }

        /***
         * 设置height
         */
        fun height( @DimenRes height: Int): Builder {
            this.height = height
            return this
        }

        /***
         * 设置width 设置了之后screenWidthPercent无效
         */
        fun width( @DimenRes width: Int): Builder {
            this.width = width
            return this
        }

        /***
         * 设置动画效果
         */
        fun anim(@StyleRes anim: Int): Builder {
            this.anim = anim
            return this
        }

        /***
         * 设置宽度占屏幕宽度百分比
         */
        fun screenWidthPercent(@FloatRange(from = 0.0, to = 1.0) p: Float): Builder {
            this.perSent = p
            return this
        }

        /***
         * 设置背景透明度
         */
        fun backgroundAlpha(@FloatRange(from = 0.0, to = 1.0) a: Float): Builder {
            this.backgroundAlpha = a
            return this
        }

        /***
         * 设置显示的位置，主要的几个位置
         *
         */
        fun gravity(@DialogGravity.BaseGravity g: Int): Builder {
            this.gravity = g
            return this
        }

        /***
         * 设置点击弹框外部是否取消弹框
         */
        fun outsideCancelable(cancelable: Boolean): Builder {
            this.cancelOutside = cancelable
            return this
        }

        /***
         * 设置tag
         */
        fun tag(tag: String): Builder {
            this.tag = tag
            return this
        }

        /***
         * 设置物理返回键和点击外部能否取消弹框，
         * 该属性为false，也会导致outsideCancelable不起作用
         */
        fun cancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        /***
         * 设置“取消”按钮的id，如果不需要在点击“取消”按钮时，有其他操作，
         * 可直接设置这个属性，如果有额外操作，可用addViewClick属性自定义添加
         */
        fun cancelId(@IdRes cancelId: Int): Builder {
            this.cancelId = cancelId
            return this
        }

        /***
         * 设置文字
         */
        fun setText(@IdRes id: Int, s : String): Builder {
            this.textMap[id] = s
            return this
        }

        /***
         * 设置点击事件
         */
        fun addViewClick(@IdRes id: Int, c: Dialog.(View) -> Unit): Builder {
            this.clickViewMap[id] = c
            return this
        }

        /***
         * 获取view，实现自定义操作，例如初始化属性
         */
        fun getView(@IdRes id: Int, c: View.(View) -> Unit):Builder{
            this.getViewMap[id] = c
            return this
        }

        fun rootView(c: Dialog.(View) -> Unit):Builder{
            rootViewFun = c
            return this
        }

        fun build(): BaseFragmentDialog {
            return BaseFragmentDialog().also {
                (layout == 0).trueLet {
                    it.layout = R.layout.dialog_base
                    it.gravity = Gravity.CENTER
                }elseLet {
                    it.layout = this@Builder.layout
                    it.gravity = this@Builder.gravity
                }
                it.fm = fragmentManager
                it.mHeight = height
                it.mWidth = width
                it.t = tag
                it.cancelId = cancelId
                it.perSent = perSent
                it.anim = anim
                it.cancelOutside = cancelOutside
                it.clickViewMap = clickViewMap
                it.getViewMap = getViewMap
                it.rootViewFun = rootViewFun
                it.textMap = textMap
                it.c = cancelable
                it.backgroundAlpha = backgroundAlpha
                fragmentManager = null
            }
        }

    }
}