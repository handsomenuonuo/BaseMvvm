package org.lib.base.base.arch

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import org.lib.base.extensions.observeNonNull
import org.lib.base.extensions.observeNullable


/**
 * @author: HuangFeng
 * @time: 2020/6/7 10:28 PM
 * @description: 基于MVVM模式的Activity的基类
 * @since: 1.0.0
 */
abstract class BaseBVMActivity<B : ViewDataBinding, VM : BaseViewModel> : BaseBindingActivity<B>(),
    ViewBehavior {

    protected lateinit var viewModel: VM
        private set

    override fun initContentView(savedInstanceState: Bundle?) {
        super.initContentView(savedInstanceState)
        injectViewModel(savedInstanceState)
        initInternalObserver()
    }

    protected fun injectViewModel(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[getViewModelClass()]
        viewModel.application = application
        lifecycle.addObserver(viewModel)
    }

    override fun onDestroy() {
        binding.unbind()
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }

    protected fun initInternalObserver() {
        viewModel._loadingEvent.observeNonNull(this) {
            showLoadingUI(it)
        }
        viewModel._emptyPageEvent.observeNonNull(this) {
            showEmptyUI(it)
        }
        viewModel._toastEvent.observeNonNull(this) {
            showToast(it)
        }
        viewModel._pageNavigationEvent.observeNonNull(this) {
            navigate(it)
        }
        viewModel._backPressEvent.observeNullable(this) {
            backPress(it)
        }
        viewModel._finishPageEvent.observeNullable(this) {
            finishPage(it)
        }
    }

    protected abstract fun getViewModelClass(): Class<VM>
}