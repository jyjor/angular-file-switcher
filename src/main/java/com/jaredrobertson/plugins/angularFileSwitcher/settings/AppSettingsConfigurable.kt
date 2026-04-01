// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.jaredrobertson.plugins.angularFileSwitcher.settings

import com.intellij.openapi.options.Configurable
import com.jaredrobertson.plugins.angularFileSwitcher.settings.AppSettingsState.Companion.instance
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

/**
 * Provides controller functionality for application settings.
 */
class AppSettingsConfigurable : Configurable {
    private var mySettingsComponent: AppSettingsComponent? = null
    override fun getDisplayName(): @Nls(capitalization = Nls.Capitalization.Title) String {
        return "Angular File Switcher"
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return mySettingsComponent!!.preferredFocusedComponent
    }

    override fun createComponent(): JComponent {
        mySettingsComponent = AppSettingsComponent()
        return mySettingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val settings = instance
        var modified = mySettingsComponent!!.classFileExtensionsText != settings.classFileExtensions
        modified =
            modified or (mySettingsComponent!!.templateFileExtensionsText != settings.templateFileExtensions)
        modified =
            modified or (mySettingsComponent!!.styleFileExtensionsText != settings.styleFileExtensions)
        modified =
            modified or (mySettingsComponent!!.testFileExtensionsText != settings.testFileExtensions)
        modified = modified or (mySettingsComponent!!.switcherGrouping !== settings.grouping)
        modified = modified or (mySettingsComponent!!.closeBehavior !== settings.closeBehavior)
        
        return modified
    }

    override fun apply() {
        val settings = instance
        settings.classFileExtensions = mySettingsComponent!!.classFileExtensionsText
        settings.templateFileExtensions = mySettingsComponent!!.templateFileExtensionsText
        settings.styleFileExtensions = mySettingsComponent!!.styleFileExtensionsText
        settings.testFileExtensions = mySettingsComponent!!.testFileExtensionsText
        settings.grouping = mySettingsComponent!!.switcherGrouping
        settings.closeBehavior = mySettingsComponent!!.closeBehavior
    }

    override fun reset() {
        val settings = instance
        mySettingsComponent!!.classFileExtensionsText = settings.classFileExtensions
        mySettingsComponent!!.templateFileExtensionsText = settings.templateFileExtensions
        mySettingsComponent!!.styleFileExtensionsText = settings.styleFileExtensions
        mySettingsComponent!!.testFileExtensionsText = settings.testFileExtensions
        mySettingsComponent!!.switcherGrouping = settings.grouping
        mySettingsComponent!!.closeBehavior = settings.closeBehavior
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}