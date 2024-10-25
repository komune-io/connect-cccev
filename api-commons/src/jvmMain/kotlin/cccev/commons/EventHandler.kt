package cccev.commons

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.dao.OptimisticLockingFailureException

open class EventHandler {

    protected val logger = LoggerFactory.getLogger(EventHandler::class.java)

    fun handleEvent(
        logMessage: String, retryOnOptimisticLockFailure: Int = 5,
        mustHandle: suspend () -> Boolean = { true }, handleError: suspend (Exception) -> Unit = {}, exec: suspend () -> Unit
    ) = GlobalScope.launch(Dispatchers.IO) {
        if (!mustHandle()) {
            return@launch
        }
        handleEvent(logMessage, retryOnOptimisticLockFailure, {}, handleError) { _ -> exec() }
    }

    fun <T> handleEvent(
        logMessage: String, retryOnOptimisticLockFailure: Int = 5,
        prepareForExecution: suspend () -> T? = { null }, handleError: suspend (Exception) -> Unit = {}, exec: suspend (T) -> Unit
    ) = GlobalScope.launch(Dispatchers.IO) {
        val preparation = prepareForExecution() ?: return@launch
        logger.info("$logMessage: Start")

        try {
            execWithRetryOnOptimisticFailure(retryOnOptimisticLockFailure, logMessage) { exec(preparation) }
            logger.info("$logMessage: End")
        } catch (e: Exception) {
            logger.error("$logMessage: Error", e)
            handleError(e)
        }
    }

    private suspend fun execWithRetryOnOptimisticFailure(availableRetries: Int, logMessage: String, exec: suspend () -> Unit) {
        try {
            exec()
        } catch (e: OptimisticLockingFailureException) {
            if (availableRetries == 0) {
                throw e
            }
            logger.info("$logMessage: OptimisticLockingFailureException, retrying operation (retries left: $availableRetries)")
            execWithRetryOnOptimisticFailure(availableRetries - 1, logMessage, exec)
        }
    }
}
