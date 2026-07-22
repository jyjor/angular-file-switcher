package com.jaredrobertson.plugins.angularFileSwitcher

import com.jaredrobertson.plugins.angularFileSwitcher.settings.AppSettingsState

class CssFileSwitchAction : TypedFileSwitchAction("Open CSS/Style File") {
    override fun getTargetExtension(): String {
        // 從AppSettingsState獲取第一個CSS擴展名
        val extensions = AppSettingsState.instance.styleFileExtensions.trim().split(" +".toRegex())
        // 優先使用scss，因為Angular常用這種擴展名
        return extensions.firstOrNull { it.contains(".scss") } 
               ?: extensions.firstOrNull { it.contains(".css") }
               ?: ".scss"
    }
} 