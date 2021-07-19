package com.dicoding.courseschedule.util

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
            1 -> MONDAY.value
            2 -> TUESDAY.value
            3 -> WEDNESDAY.value
            4 -> THURSDAY.value
            5 -> FRIDAY.value
            6 -> SATURDAY.value
            7 -> SUNDAY.value
            else -> MONDAY.value
        }
    }
}