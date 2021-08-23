package com.steven.androidmediaplayer

/**
 * author: zhoufan
 * data: 2021/8/20 11:19
 * content:
 */
class TimeUtil {

    companion object {

        /**
         * 秒数转换为时分秒格式
         *
         * @param second
         * @return
         */
        fun getTime(second: Int): String? {
            val builder = StringBuilder()
            val secondValue = second  / 60
            val minuteValue = second  - secondValue * 60
            if (secondValue == 0) {
                builder.append("00")
            } else {
                if (secondValue < 10) {
                    builder.append("0").append(secondValue)
                } else {
                    builder.append(secondValue)
                }
            }
            builder.append(":")
            if (minuteValue == 0) {
                builder.append("00")
            } else {
                if (minuteValue < 10) {
                    builder.append("0").append(minuteValue)
                } else {
                    builder.append(minuteValue)
                }
            }
            return builder.toString()
        }
    }
}