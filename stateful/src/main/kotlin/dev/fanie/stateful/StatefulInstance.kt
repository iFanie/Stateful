package dev.fanie.stateful

/**
 * Stateful wrapper for a given [Model] type, operating with a single instance.
 */
abstract class StatefulInstance<Model : Any>(
    initialInstance: Model? = null
) : StatefulUpdateReceiver<Model>, StatefulUpdateNotifier<Model> {
    private var instance: Model? = null

    init {
        if (initialInstance != null) {
            accept(initialInstance)
        }
    }

    final override fun accept(newInstance: Model) {
        announce(instance, newInstance)
        instance = newInstance
    }

    final override fun clear() {
        instance = null
    }
}
