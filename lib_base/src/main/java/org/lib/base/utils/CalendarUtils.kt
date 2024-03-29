package org.lib.base.utils

import com.blankj.utilcode.util.TimeUtils
import java.text.SimpleDateFormat
import java.util.*

/**********************************
 * @Name:         CalendarUtils
 * @Copyright：  CreYond
 * @CreateDate： 2021/5/4 12:29
 * @author:      HuangFeng
 * @Version：    1.0
 * @Describe:
 *
 **********************************/
class CalendarUtils {

    companion object{

        @JvmStatic
        fun createCalendar(dateString: String?): Calendar? {
            var calendar = Calendar.getInstance()
            val date: Date = TimeUtils.string2Date(dateString, SimpleDateFormat("yyyy-MM-dd"))
            calendar = Calendar.getInstance()
            calendar.timeInMillis = date.time
            return calendar
        }
    }
}