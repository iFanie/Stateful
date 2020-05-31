package dev.fanie.stateful

/**
 * Decorates classes for which changes need to be monitored. The processor of this annotation will
 * generate a wrapper class for handling diffing between the previous and next instance of the class
 * and an interface for receiving updates on the changes.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Stateful(
    /**
     * The type of Stateful wrapper to be created. Default value is {@code StatefulType.INSTANCE}.
     */
    val type: StatefulType = StatefulType.INSTANCE,

    /**
     * Options for the generated code. Default value is none.
     */
    val options: Array<StatefulOptions> = []
)

/**
 * The different types of Stateful wrappers that can be created.
 */
enum class StatefulType {
    /**
     * Wrapper with a single cached instance for performing diffing and announcing.
     */
    INSTANCE,

    /**
     * Wrapper with a stack of cached instances for performing diffing and announcing and the ability to roll back to a
     * previously accepted instance.
     */
    STACK,

    /**
     * Wrapper with a linked list of cached instances for performing diffing and announcing and the ability to roll back and
     * forth between the nodes of instances.
     */
    LINKED_LIST
}

/**
 * Options that can be applied for the code generation.
 */
enum class StatefulOptions {
    /**
     * When applied, the top level functions for lazy delegation of the generated wrapper initialization will not be
     * generated alongside the synchronous top level initializer.
     */
    NO_LAZY_INIT,

    /**
     * When applied, only a single listener interface will be generated containing callbacks for every single public
     * property in the annotated model, instead of separate interfaces, one for each public property of the annotated model,
     * all of which are extended by a master interface.
     */
    NON_CASCADING_LISTENER,

    /**
     * When applied, no diffing will be performed on the properties of the annotated model and the listener will be invoked
     * for all the properties on every new instance received.
     */
    NO_DIFFING
}
