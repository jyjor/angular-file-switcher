// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.jaredrobertson.plugins.angularFileSwitcher.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import com.jaredrobertson.plugins.angularFileSwitcher.models.CloseBehavior
import com.jaredrobertson.plugins.angularFileSwitcher.models.Grouping

/**
 * Supports storing the application settings in a persistent way.
 * The [State] and [Storage] annotations define the name of the data and the file name where
 * these persistent application settings are stored.
 */
@State(
    name = "com.jaredrobertson.plugins.angularFileSwitcher.settings.AppSettingsState",
    storages = [Storage("SdkSettingsPlugin.xml")]
)
class AppSettingsState : PersistentStateComponent<AppSettingsState?> {
    @JvmField
    var classFileExtensions = DEFAULT_CLASS_FILE_EXTENSIONS

    @JvmField
    var templateFileExtensions = DEFAULT_TEMPLATE_FILE_EXTENSIONS

    @JvmField
    var styleFileExtensions = DEFAULT_STYLE_FILE_EXTENSIONS

    @JvmField
    var testFileExtensions = DEFAULT_TEST_FILE_EXTENSIONS

    @JvmField
    var grouping = DEFAULT_SWITCHER_GROUPING

    @JvmField
    var closeBehavior = DEFAULT_CLOSE_BEHAVIOR
    
    override fun getState(): AppSettingsState {
        return this
    }

    override fun loadState(state: AppSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        private const val DEFAULT_CLASS_FILE_EXTENSIONS = ".ts .js"
        private const val DEFAULT_TEMPLATE_FILE_EXTENSIONS = ".html .php .pug .slim"
        private const val DEFAULT_STYLE_FILE_EXTENSIONS = ".css .sass .scss .less .styl"
        private const val DEFAULT_TEST_FILE_EXTENSIONS =
            ".test.js .test.ts .spec.js .spec.ts _spec.js _spec.ts"
        private val DEFAULT_SWITCHER_GROUPING = Grouping.TAB_GROUP
        private val DEFAULT_CLOSE_BEHAVIOR = CloseBehavior.ONLY_ON_ACTION

        @JvmStatic
        val instance: AppSettingsState
            get() = ApplicationManager.getApplication().getService(AppSettingsState::class.java)
    }
}