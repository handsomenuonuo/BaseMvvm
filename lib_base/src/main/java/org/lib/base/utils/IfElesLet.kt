package org.lib.base.utils

/**********************************
 * @Name:         IfElesLet
 * @Copyright：  CreYond
 * @CreateDate： 2021/4/28 14:46
 * @author:      HuangFeng
 * @Version：    1.0
 * @Describe:
 *
 **********************************/
inline infix fun Boolean.trueLet(trueBlock: Boolean.() -> Unit): Else {
    if (this) {
        trueBlock()
        return NotDoElse(this)
    }
    return DoElse(this)
}

inline infix fun Boolean.falseLet(falseBlock: Boolean.() -> Unit): Else {
    if (!this) {
        falseBlock()
        return NotDoElse(this)
    }
    return DoElse(this)
}

interface Else {
    infix fun elseLet(elseBlock: Boolean.() -> Unit)
}

class DoElse(private val boolean: Boolean) : Else {
    override infix fun elseLet(elseBlock: Boolean.() -> Unit) {
        elseBlock(boolean)
    }
}

class NotDoElse(private val boolean: Boolean) : Else {
    override infix fun elseLet(elseBlock: Boolean.() -> Unit) {
    }
}