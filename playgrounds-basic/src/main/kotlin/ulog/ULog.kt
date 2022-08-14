package pl.mareklangiewicz.ulog

import android.util.*

fun ulogd(data: Any?) = Log.d("ulog", data.toString())
fun ulogi(data: Any?) = Log.i("ulog", data.toString())
fun ulogw(data: Any?) = Log.w("ulog", data.toString())
fun uloge(data: Any?) = Log.e("ulog", data.toString())

