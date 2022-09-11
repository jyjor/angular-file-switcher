package com.jaredrobertson.plugins.angularFileSwitcher.models

enum class Grouping(private val message: String) {
    TAB_GROUP("Within the same editor group"),
    EVERYWHERE("Everywhere");

    override fun toString(): String {
        return message
    }
}