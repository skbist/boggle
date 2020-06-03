package exceptions

import org.slf4j.LoggerFactory
import java.io.IOException
import java.time.LocalDateTime
import javax.management.OperationsException

fun handleException(e: Exception): Map<String, Any> {
    // e.printStackTrace()
    val logger = LoggerFactory.getLogger("ExceptionHandler")
    val responseMsg: String
    var code = 500
    when (e) {
        is NullPointerException -> {
            logger.error(LocalDateTime.now().toString() + " - You stumbled upon void", e.message)
            responseMsg = "Null pointer exception"
        }
        is NumberFormatException -> {
            logger.error(LocalDateTime.now().toString() + " - Illegal Argument Exception", e.message)
            responseMsg = "Illegal Argument Exception"
        }
        is RuntimeException -> {
            logger.error(LocalDateTime.now().toString() + " - Unknown Runtime Exception", e.message)
            responseMsg = e.message ?: "Unknown Runtime Exception"
        }
        is IOException -> {
            logger.error(LocalDateTime.now().toString() + " - File read IOException", e.message)
            responseMsg = e.message ?: "file read difficulty IOException"
        }
        is IllegalArgumentException -> {
            logger.error(LocalDateTime.now().toString() + "Argument mismatch Exception", e.message)
            responseMsg = e.message ?: "Argument mismatch Exception"
        }
        is OperationsException -> {
            logger.error(LocalDateTime.now().toString() + "Exception Occured during database operation", e.message)
            responseMsg = e.message ?: "Exception Occured during database operation"
        }
        else -> {
            logger.error(LocalDateTime.now().toString() + " - Unknown Error", e.message)
            responseMsg = "Unknown Error"
        }
    }
    return mapOf(
        "success" to false,
        "status" to code,
        "msg" to responseMsg
    )
}

