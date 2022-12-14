// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.jaredrobertson.plugins.angularFileSwitcher.settings

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.jaredrobertson.plugins.angularFileSwitcher.models.CloseBehavior
import com.jaredrobertson.plugins.angularFileSwitcher.models.Grouping
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * Supports creating and managing a [JPanel] for the Settings Dialog.
 */
class AppSettingsComponent {
    val panel: JPanel
    private val myClassFileExtensionsText = JBTextField()
    private val myTemplateFileExtensionsText = JBTextField()
    private val myStyleFileExtensionsText = JBTextField()
    private val myTestFileExtensionsText = JBTextField()
    private val mySwitcherGroupingCombo = ComboBox(Grouping.values(), 240)
    private val myCloseBehaviorCombo = ComboBox(CloseBehavior.values(), 240)

    init {
        val fileExtensionTypePanel = FormBuilder.createFormBuilder()
            .addLabeledComponent("Class: ", myClassFileExtensionsText, 5, false)
            .addLabeledComponent("Template: ", myTemplateFileExtensionsText, 5, false)
            .addLabeledComponent("Style: ", myStyleFileExtensionsText, 5, false)
            .addLabeledComponent("Test: ", myTestFileExtensionsText, 5, false)
            .panel
        fileExtensionTypePanel.border = IdeBorderFactory.createTitledBorder("File Extension Types")
        val otherSettingsPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(
                "Open and close same component files: ",
                mySwitcherGroupingCombo,
                5,
                false
            )
            .addLabeledComponent("Close other component files: ", myCloseBehaviorCombo, 5, false)
            .panel
        otherSettingsPanel.border = IdeBorderFactory.createTitledBorder("Other Settings")
        panel = FormBuilder.createFormBuilder()
            .addComponent(fileExtensionTypePanel)
            .addComponent(otherSettingsPanel)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    val preferredFocusedComponent: JComponent
        get() = myClassFileExtensionsText
    var classFileExtensionsText: String
        get() = myClassFileExtensionsText.text
        set(newText) {
            myClassFileExtensionsText.text = newText
        }
    var templateFileExtensionsText: String
        get() = myTemplateFileExtensionsText.text
        set(newText) {
            myTemplateFileExtensionsText.text = newText
        }
    var styleFileExtensionsText: String
        get() = myStyleFileExtensionsText.text
        set(newText) {
            myStyleFileExtensionsText.text = newText
        }
    var testFileExtensionsText: String
        get() = myTestFileExtensionsText.text
        set(newText) {
            myTestFileExtensionsText.text = newText
        }
    var switcherGrouping: Grouping
        get() = mySwitcherGroupingCombo.item
        set(newGrouping) {
            mySwitcherGroupingCombo.item = newGrouping
        }
    var closeBehavior: CloseBehavior
        get() = myCloseBehaviorCombo.item
        set(newCloseBehavior) {
            myCloseBehaviorCombo.item = newCloseBehavior
        }
}