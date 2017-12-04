package individual.professortao.utils

// author: ProfessorTao

import java.util.Calendar
import java.util.Date
import java.text.SimpleDateFormat
import scala.collection.mutable.ArrayBuffer

object DateUtil {
    def calendar_to_date(the_cal: Calendar): Date = {
        the_cal.getTime()
    }

    def date_to_calendar(the_date: Date): Calendar = {
        val the_cal = Calendar.getInstance()
        the_cal.setTime(the_date)
        the_cal
    }

    def date_str_to_date(date_str: String, format_str: String = "yyyy-MM-dd"): Date = {
        val date_format = new SimpleDateFormat(format_str)
        date_format.parse(date_str)
    }

    def date_str_to_calendar(date_str: String, format_str: String = "yyyy-MM-dd"): Calendar = {
        val date_format = new SimpleDateFormat(format_str)
        val the_cal = Calendar.getInstance()
        date_format.setTimeZone(the_cal.getTimeZone())
        val the_date = date_format.parse(date_str)
        the_cal.setTime(the_date)
        the_cal
    }

    def date_to_date_str(the_date: Date, format_str: String = "yyyy-MM-dd"): String = {
        val date_format = new SimpleDateFormat(format_str)
        date_format.format(the_date)
    }

    def calendar_to_date_str(the_cal: Calendar, format_str: String = "yyyy-MM-dd"): String = {
        val date_format = new SimpleDateFormat(format_str)
        val the_date = calendar_to_date(the_cal)
        date_format.format(the_date)
    }

    def get_current_unix_timestamp(): Long = {
        System.currentTimeMillis / 1000
    }

    def calendar_to_unix_timestamp(the_cal: Calendar): Long = {
        the_cal.getTimeInMillis() / 1000
    }

    def date_to_unix_timestamp(the_date: Date): Long = {
        the_date.getTime() / 1000
    }

    def date_str_to_unix_timestamp(date_str: String, format_str: String = "yyyy-MM-dd"): Long = {
        // val the_cal = date_str_to_calendar(date_str, format_str)
        // calendar_to_unix_timestamp(the_cal)
        val the_date = date_str_to_date(date_str, format_str)
        date_to_unix_timestamp(the_date)
    }

    def get_date_str_from_unix_timestamp(timestamp: Long, format_str: String = "yyyy-MM-dd"): String = {
        val date_format = new SimpleDateFormat(format_str)
        date_format.format(timestamp*1000)
    }

    def get_date_after_today(nAfter: Int = 0): Date = {
        // 获取从今天始后n天信息
        val now = Calendar.getInstance()
        now.add(Calendar.DATE, nAfter);
        calendar_to_date(now)
    }

    def get_calendar_after_today(nAfter: Int = 0): Calendar = {
        // 获取从今天始后n天信息
        val now = Calendar.getInstance()
        now.add(Calendar.DATE, nAfter);
        now
    }

    def get_date_str_after_today(nAfter: Int = 0, format_str: String = "yyyy-MM-dd"): String = {
        // 获取从今天始后n天信息
        val date_format = new java.text.SimpleDateFormat(format_str)
        val the_cal = get_calendar_after_today(nAfter)
        calendar_to_date_str(the_cal, format_str)
    }

    def get_date_str_after_calendar(the_cal: Calendar, nAfter: Int = 0, format_str: String = "yyyy-MM-dd"): String = {
        // 获取指定日期后n天信息
        the_cal.add(Calendar.DATE, nAfter)
        calendar_to_date_str(the_cal, format_str)
    }

    def get_date_str_after_date(the_date: Date, nAfter: Int = 0, format_str: String = "yyyy-MM-dd"): String = {
        // 获取指定日期后n天信息
        val the_cal = date_to_calendar(the_date)
        the_cal.add(Calendar.DATE, nAfter)
        calendar_to_date_str(the_cal, format_str)
    }

    def get_date_str_after_date_str(date_str: String, nAfter: Int = 0, format_str: String = "yyyy-MM-dd"): String = {
        // 获取指定日期后n天信息
        val the_cal = date_str_to_calendar(date_str, format_str)
        the_cal.add(Calendar.DATE, nAfter)
        calendar_to_date_str(the_cal, format_str)
    }

    def get_date_str_list_between_date_str(date_str_start: String, date_str_end: String, format_str: String = "yyyy-MM-dd"): Array[String] = {
        val timestamp_start = date_str_to_unix_timestamp(date_str_start, format_str)
        val timestamp_end = date_str_to_unix_timestamp(date_str_end, format_str)
        val date_str_list = ArrayBuffer[String]()
        var timestamp_temp = timestamp_start
        while (timestamp_temp <= timestamp_end) {
            val date_str = get_date_str_from_unix_timestamp(timestamp_temp, format_str)
            date_str_list += date_str
            timestamp_temp += 86400
        }
        date_str_list.toArray
    }

    // 将默认的date_format设置为"yyyyMMdd" 常用函数
    val date_intstr_to_date = date_str_to_date(_: String, "yyyyMMdd")
    val date_intstr_to_calendar = date_str_to_calendar(_: String, "yyyyMMdd")
    val date_to_date_intstr = date_to_date_str(_: Date, "yyyyMMdd")
    val calendar_to_date_intstr = calendar_to_date_str(_: Calendar, "yyyyMMdd")
    val date_intstr_to_unix_timestamp = date_str_to_unix_timestamp(_: String, "yyyyMMdd")
    val get_date_intstr_from_unix_timestamp = get_date_str_from_unix_timestamp(_: Long, "yyyyMMdd")
    val get_date_intstr_after_today = get_date_str_after_today(_: Int, "yyyyMMdd")
    val get_date_intstr_after_calendar = get_date_str_after_calendar(_: Calendar, _: Int, "yyyyMMdd")
    val get_date_intstr_after_date = get_date_str_after_date(_: Date, _: Int, "yyyyMMdd")
    val get_date_intstr_after_date_str = get_date_str_after_date_str(_: String, _: Int, "yyyyMMdd")
    val get_date_intstr_list_between_date_intstr = get_date_str_list_between_date_str(_: String, _: String, "yyyyMMdd")
}






