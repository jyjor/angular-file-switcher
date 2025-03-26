package com.jaredrobertson.plugins.angularFileSwitcher

import com.jaredrobertson.plugins.angularFileSwitcher.settings.AppSettingsState

class HtmlFileSwitchAction : TypedFileSwitchAction("Open HTML Template File") {
    override fun getTargetExtension(): String {
        // 從AppSettingsState獲取第一個HTML擴展名
        val extensions = AppSettingsState.instance.templateFileExtensions.trim().split(" +".toRegex())
        return extensions.firstOrNull { it.contains(".html") } ?: ".html"
    }
} 