package dev.fanie.stateful

/**
 * Receives new instances of a certain type and handles the acceptance flow of it.
 */
interface StatefulUpdateReceiver<Model> {
    /**
     * Accepts a new instance of the [Model] type.
     * @param newInstance The new instance to be accepted.
     */
    fun accept(newInstance: Model)

    /**
     * Removes any cached instances of the [Model] type.
     */
    fun clear()
}
