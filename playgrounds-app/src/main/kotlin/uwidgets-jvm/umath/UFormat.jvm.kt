package pl.mareklangiewicz.umath

/*actual*/ fun Float.str(precision: Int): String = "%.${precision}f".format(this)
/*actual*/ fun Double.str(precision: Int): String = "%.${precision}f".format(this)
