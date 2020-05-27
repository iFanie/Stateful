package dev.fanie.statefulcompiler.util

/**
 * Always throws [NotImplementedError] stating that operation will not be implemented.
 *
 * @param reason a string explaining why the implementation will not be implemented.
 */
internal fun wontDo(reason: String = "Not needed for tests"): Nothing =
    throw NotImplementedError("The operation will not be implemented implemented: $reason")