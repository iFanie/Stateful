package dev.fanie.stateful

/**
 * Decorates classes for which changes need to be monitored. The processor of this annotation will
 * generate a wrapper class for handling diffing between the previous and next instance of the class
 * and an interface for receiving updates on the changes.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Stateful(
    val type: StatefulType = StatefulType.INSTANCE
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
    STACK
}
