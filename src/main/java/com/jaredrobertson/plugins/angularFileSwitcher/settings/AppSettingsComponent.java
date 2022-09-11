// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.jaredrobertson.plugins.angularFileSwitcher.settings;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import com.jaredrobertson.plugins.angularFileSwitcher.models.CloseBehavior;
import com.jaredrobertson.plugins.angularFileSwitcher.models.Grouping;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent {

    private final JPanel myMainPanel;
    private final JBTextField myClassFileExtensionsText = new JBTextField();
    private final JBTextField myTemplateFileExtensionsText = new JBTextField();
    private final JBTextField myStyleFileExtensionsText = new JBTextField();
    private final JBTextField myTestFileExtensionsText = new JBTextField();

    private final ComboBox<Grouping> mySwitcherGroupingCombo = new ComboBox<>(Grouping.values(), 240);
    private final ComboBox<CloseBehavior> myCloseBehaviorCombo = new ComboBox<>(CloseBehavior.values(), 240);

    public AppSettingsComponent() {
        JPanel fileExtensionTypePanel = FormBuilder.createFormBuilder()
                .addLabeledComponent("Class: ", myClassFileExtensionsText, 5, false)
                .addLabeledComponent("Template: ", myTemplateFileExtensionsText, 5, false)
                .addLabeledComponent("Style: ", myStyleFileExtensionsText, 5, false)
                .addLabeledComponent("Test: ", myTestFileExtensionsText, 5, false)
                .getPanel();
        fileExtensionTypePanel.setBorder(IdeBorderFactory.createTitledBorder("File Extension Types"));

        JPanel otherSettingsPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent("Open and close same component files: ", mySwitcherGroupingCombo, 5, false)
                .addLabeledComponent("Close other component files: ", myCloseBehaviorCombo, 5, false)
                .getPanel();
        otherSettingsPanel.setBorder(IdeBorderFactory.createTitledBorder("Other Settings"));

        myMainPanel = FormBuilder.createFormBuilder()
                .addComponent(fileExtensionTypePanel)
                .addComponent(otherSettingsPanel)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return myClassFileExtensionsText;
    }

    @NotNull
    public String getClassFileExtensionsText() {
        return myClassFileExtensionsText.getText();
    }

    public void setClassFileExtensionsText(@NotNull String newText) {
        myClassFileExtensionsText.setText(newText);
    }

    @NotNull
    public String getTemplateFileExtensionsText() {
        return myTemplateFileExtensionsText.getText();
    }

    public void setTemplateFileExtensionsText(@NotNull String newText) {
        myTemplateFileExtensionsText.setText(newText);
    }

    @NotNull
    public String getStyleFileExtensionsText() {
        return myStyleFileExtensionsText.getText();
    }

    public void setStyleFileExtensionsText(@NotNull String newText) {
        myStyleFileExtensionsText.setText(newText);
    }

    @NotNull
    public String getTestFileExtensionsText() {
        return myTestFileExtensionsText.getText();
    }

    public void setTestFileExtensionsText(@NotNull String newText) {
        myTestFileExtensionsText.setText(newText);
    }

    @NotNull
    public Grouping getSwitcherGrouping() {
        return mySwitcherGroupingCombo.getItem();
    }

    public void setSwitcherGrouping(@NotNull Grouping newGrouping) {
        mySwitcherGroupingCombo.setItem(newGrouping);
    }

    @NotNull
    public CloseBehavior getCloseBehavior() {
        return myCloseBehaviorCombo.getItem();
    }

    public void setCloseBehavior(@NotNull CloseBehavior newCloseBehavior) {
        myCloseBehaviorCombo.setItem(newCloseBehavior);
    }
}
