package cccev.dsl.model


fun <T> List<T>?.nullIfEmpty(): List<T>? = this?.takeIf { it.isNotEmpty() }
fun <T, R> Map<T, R>?.nullIfEmpty(): Map<T, R>? = this?.takeIf { it.isNotEmpty() }
