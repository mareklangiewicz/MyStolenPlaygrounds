package pl.mareklangiewicz.umath

// TODO_later: more multi-platform formatting options
/*
expect fun Float.str(precision: Int = 2): String
*/
/*
expect fun Double.str(precision: Int = 2): String

*/
val Float.str get() = str(2)
val Double.str get() = str(2)

val Number.int get() = toInt()
val Number.dbl get() = toDouble()

