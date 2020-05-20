package dev.fanie.stateful

/**
 * Decorates classes for which changes need to be monitored. The processor of this annotation will
 * generate a wrapper class for handling diffing between the previous and next instance of the class
 * and an interface for receiving updates on the changes.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Stateful
