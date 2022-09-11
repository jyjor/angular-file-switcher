package com.jaredrobertson.plugins.angularFileSwitcher.models

enum class CloseBehavior(private val text: String) {
    NEVER("Never"),
    ONLY_ON_ACTION("Only on file switcher action"),
    ALWAYS("Always");

    override fun toString(): String {
        return text
    }
}