package org.hf.basemvvm

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import org.lib.base.base.arch.BaseViewModel
import kotlin.random.Random


/**********************************
 * @Name:         MainViewModel
 * @Copyright：  Antoco
 * @CreateDate： 2024/3/29 16:59
 * @author:      huang feng
 * @Version：    1.0
 * @Describe:
 **********************************/
class MainViewModel(val savedStateHandle:SavedStateHandle) : BaseViewModel() {

    var text = savedStateHandle.getLiveData<String>("123")



    fun onButtonClick(view: View) {
        // 处理按钮点击事件的逻辑
        Log.e("test","MainViewModel onButtonClick $savedStateHandle")
        text.value = Random.nextInt().toString()

    }
}