package dev.fanie.stateful

/**
 * Receives new instances of a certain type and handles the announcement flow of it.
 */
interface StatefulUpdateNotifier<Model> {
    /**
     * Announces of the existence of a new instance of the [Model] type.
     * @param currentInstance The current instance, if any.
     * @param newInstance The instance to be announced.
     */
    fun announce(currentInstance: Model?, newInstance: Model)
}
