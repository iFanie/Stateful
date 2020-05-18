package dev.fanie.statefulcompiler

/**
 * Always throws [NotImplementedError] stating that operation will not be implemented.
 *
 * @param reason a string explaining why the implementation will not be implemented.
 */
internal fun WONTDO(reason: String = "Not needed for tests"): Nothing =
    throw NotImplementedError("The operation will not be implemented implemented: $reason")