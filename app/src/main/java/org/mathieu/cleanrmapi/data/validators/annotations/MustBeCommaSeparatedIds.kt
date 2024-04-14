package org.mathieu.cleanrmapi.data.validators.annotations


@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
/**
 * Indicates that the annotated string should follow a specific format:
 * a comma-separated list of numeric IDs. For example: "1,2,3".
 *
 * This annotation is intended to be used on strings representing
 * lists of IDs in API parameters or data properties to ensure
 * consistency in data formatting and handling.
 *
 * Note: This annotation serves as documentation and a guideline for developers.
 * Actual validation of the string format should be implemented separately.
 */
annotation class MustBeCommaSeparatedIds
