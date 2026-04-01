package com.jaredrobertson.plugins.angularFileSwitcher

import com.jaredrobertson.plugins.angularFileSwitcher.settings.AppSettingsState

class TestFileSwitchAction : TypedFileSwitchAction("Open Test File") {
    override fun getTargetExtension(): String {
        // 從AppSettingsState獲取第一個測試擴展名
        val extensions = AppSettingsState.instance.testFileExtensions.trim().split(" +".toRegex())
        return extensions.firstOrNull { it.contains(".spec.ts") } ?: ".spec.ts"
    }
} 