package com.ticketswapassessment.navigation

sealed class Destination(val route: String) {
    data object Home : Destination("home")
    data object Details : Destination("details/{objectNumber}") {
        fun withObjectNumber(objectNumber: String): String {
            return this.route.replace(oldValue = "{objectNumber}", newValue = objectNumber)
        }
    }
    data object ImageViewer : Destination("imageViewer")
}