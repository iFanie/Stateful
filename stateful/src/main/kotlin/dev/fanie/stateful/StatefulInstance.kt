package dev.fanie.stateful

/**
 * Stateful wrapper for a given [Model] type, operating with a single instance.
 */
interface StatefulInstance<Model : Any> : StatefulUpdateReceiver<Model>, StatefulUpdateNotifier<Model>

/**
 * Partial implementation of the [StatefulInstance] interface, without an implementation for the
 * [StatefulUpdateNotifier.announce] function.
 */
abstract class AbstractStatefulInstance<Model : Any>(initialInstance: Model? = null) : StatefulInstance<Model> {
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
