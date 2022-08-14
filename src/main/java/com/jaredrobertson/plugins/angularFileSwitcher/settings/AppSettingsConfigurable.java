// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.jaredrobertson.plugins.angularFileSwitcher.settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Provides controller functionality for application settings.
 */
public class AppSettingsConfigurable implements Configurable {
    private AppSettingsComponent mySettingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Angular File Switcher";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new AppSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        AppSettingsState settings = AppSettingsState.getInstance();
        boolean modified = !mySettingsComponent.getClassFileExtensionsText().equals(settings.classFileExtensions);
        modified |= !mySettingsComponent.getTemplateFileExtensionsText().equals(settings.templateFileExtensions);
        modified |= !mySettingsComponent.getStyleFileExtensionsText().equals(settings.styleFileExtensions);
        modified |= !mySettingsComponent.getTestFileExtensionsText().equals(settings.testFileExtensions);
        modified |= mySettingsComponent.getSwitcherGrouping() != settings.switcherGrouping;
        modified |= mySettingsComponent.getCloseBehavior() != settings.closeBehavior;
        return modified;
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.classFileExtensions = mySettingsComponent.getClassFileExtensionsText();
        settings.templateFileExtensions = mySettingsComponent.getTemplateFileExtensionsText();
        settings.styleFileExtensions = mySettingsComponent.getStyleFileExtensionsText();
        settings.testFileExtensions = mySettingsComponent.getTestFileExtensionsText();
        settings.switcherGrouping = mySettingsComponent.getSwitcherGrouping();
        settings.closeBehavior = mySettingsComponent.getCloseBehavior();
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        mySettingsComponent.setClassFileExtensionsText(settings.classFileExtensions);
        mySettingsComponent.setTemplateFileExtensionsText(settings.templateFileExtensions);
        mySettingsComponent.setStyleFileExtensionsText(settings.styleFileExtensions);
        mySettingsComponent.setTestFileExtensionsText(settings.testFileExtensions);
        mySettingsComponent.setSwitcherGrouping(settings.switcherGrouping);
        mySettingsComponent.setCloseBehavior(settings.closeBehavior);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}
