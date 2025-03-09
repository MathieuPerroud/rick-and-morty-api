package org.mathieu.cleanrmapi.data.validators

object IdListValidator {
    private val pattern = Regex("^\\d+(,\\d+)*$")

    /**
     * Validates if the given string matches the expected format of a comma-separated list of numeric IDs.
     *
     * @param ids The string to validate.
     * @return true if the string matches the expected format, false otherwise.
     */
    private fun isValid(ids: String): Boolean = ids.matches(pattern)

    /**
     * Asserts that the given string matches the expected format of a comma-separated list of numeric IDs.
     *
     * @param ids The string to validate.
     * @throws IllegalArgumentException if the string does not match the expected format.
     */
    fun assertValid(ids: String) {
        if (!isValid(ids)) {
            throw IllegalArgumentException("The string '$ids' does not match the expected format of a comma-separated list of numeric IDs.")
        }
    }
}
