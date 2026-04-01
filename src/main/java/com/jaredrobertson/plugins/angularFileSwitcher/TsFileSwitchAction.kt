package com.jaredrobertson.plugins.angularFileSwitcher

import com.jaredrobertson.plugins.angularFileSwitcher.settings.AppSettingsState

class TsFileSwitchAction : TypedFileSwitchAction("Open TypeScript File") {
    override fun getTargetExtension(): String {
        // 從AppSettingsState獲取第一個TS擴展名
        val extensions = AppSettingsState.instance.classFileExtensions.trim().split(" +".toRegex())
        return extensions.firstOrNull { it.contains(".ts") && !it.contains(".spec.ts") && !it.contains(".test.ts") } ?: ".ts"
    }
} 