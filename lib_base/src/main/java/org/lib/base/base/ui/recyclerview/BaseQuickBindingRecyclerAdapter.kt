package org.lib.base.base.ui.recyclerview

import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**********************************
 * @Name:         BaseQuickBindingRecyclerAdapter
 * @Copyright：  CreYond
 * @CreateDate： 2021/5/7 17:13
 * @author:      HuangFeng
 * @Version：    1.0
 * @Describe:
 *
 **********************************/
abstract class BaseQuickBindingRecyclerAdapter<B : ViewDataBinding, T>
@JvmOverloads constructor(@LayoutRes private val layoutResId: Int, data: MutableList<T>? = null)
    : BaseQuickAdapter<T, BaseDataBindingHolder<B>>(layoutResId, data) {

    private lateinit var onListChangedCallback :  ObservableList.OnListChangedCallback<ObservableArrayList<T>>

    init {
        initOnListChangedCallback()
    }
    override fun convert(holder: BaseDataBindingHolder<B>, item: T) {
        var binding : B = holder.dataBinding as B
        onConvert( holder, item, emptyList())
        binding.executePendingBindings()//防止列表闪烁
    }

    override fun convert(holder: BaseDataBindingHolder<B>, item: T, payloads: List<Any>) {
        var binding : B = holder.dataBinding as B
        onConvert(holder, item,payloads)
        binding.executePendingBindings()//防止列表闪烁
    }

    abstract fun onConvert( holder: BaseDataBindingHolder<B>, item: T,@Nullable payloads: List<Any>)


    /***
     * 该方法设置ObservableArrayList，并且监听变动
     *
     */
    fun setObservableNewData(observableList: ObservableArrayList<T>){
        setDiffNewData(observableList)
        observableList.addOnListChangedCallback(onListChangedCallback)
    }


    /***
     * 这个方法仅当设置了DiffUtil.ItemCallback才会生效
     */
    fun setMDiffData(list:MutableList<T>){
        if(data.isEmpty()){
            setNewInstance(list)
        }else{
            setDiffNewData(list)
        }
    }

    /***
     * 这个方法仅当设置了DiffUtil.ItemCallback才会生效
     */
    fun setMDiffData(list:ObservableArrayList<T>){
        list.addOnListChangedCallback(onListChangedCallback)
        if(data.isEmpty()){
            setNewInstance(list)
        }else{
            setDiffNewData(list)
        }
    }

    private fun initOnListChangedCallback(){
        onListChangedCallback = object : ObservableList.OnListChangedCallback<ObservableArrayList<T>>(){
            override fun onChanged(sender: ObservableArrayList<T>?) {
                LogUtils.e("onChanged")
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(
                sender: ObservableArrayList<T>?,
                positionStart: Int,
                itemCount: Int
            ) {
                LogUtils.e("onItemRangeChanged")
                notifyItemRangeChanged(positionStart,itemCount)
            }

            override fun onItemRangeInserted(
                sender: ObservableArrayList<T>?,
                positionStart: Int,
                itemCount: Int
            ) {
                LogUtils.e("onItemRangeInserted $positionStart    $itemCount")
                notifyItemRangeInserted(positionStart,itemCount)
            }

            override fun onItemRangeMoved(
                sender: ObservableArrayList<T>?,
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) {
                LogUtils.e("onItemRangeMoved")

            }

            override fun onItemRangeRemoved(
                sender: ObservableArrayList<T>?,
                positionStart: Int,
                itemCount: Int
            ) {
                LogUtils.e("onItemRangeRemoved")
                notifyItemRangeRemoved(positionStart,itemCount)
            }

        }
    }

}

