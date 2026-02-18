package dev.xnative.cleanrmapi.navigation

interface Router {

    fun <T: Destination> navigateTo(destination: T)

    fun navigateBack()

}