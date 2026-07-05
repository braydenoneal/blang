class Logger {
    fun error(message: String, exception: Exception) {
        println("$message, ${exception.message}")
    }
}
