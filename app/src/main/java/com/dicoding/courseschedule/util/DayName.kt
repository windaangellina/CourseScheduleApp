package com.dicoding.courseschedule.util

import java.util.*

enum class DayName(val value: String) {

    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    companion object {
        fun getByNumber(dayNumber: Int) : String = when (dayNumber) {
            Calendar.MONDAY -> MONDAY.value
            Calendar.TUESDAY -> TUESDAY.value
            Calendar.WEDNESDAY -> WEDNESDAY.value
            Calendar.THURSDAY -> THURSDAY.value
            Calendar.FRIDAY -> FRIDAY.value
            Calendar.SATURDAY -> SATURDAY.value
            Calendar.SUNDAY -> SUNDAY.value
            else -> MONDAY.value
        }

        fun getByDayName(dayName : String) : Int = when(dayName) {
            MONDAY.value -> {
                Calendar.MONDAY
            }
            TUESDAY.value -> {
                Calendar.TUESDAY
            }
            WEDNESDAY.value -> {
                Calendar.WEDNESDAY
            }
            THURSDAY.value -> {
                Calendar.THURSDAY
            }
            FRIDAY.value -> {
                Calendar.FRIDAY
            }
            SATURDAY.value -> {
                Calendar.SATURDAY
            }
            SUNDAY.value -> {
                Calendar.SUNDAY
            }
            else -> Calendar.MONDAY
        }
    }
}