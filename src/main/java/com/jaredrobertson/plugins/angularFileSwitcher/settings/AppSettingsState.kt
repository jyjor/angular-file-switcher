// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.jaredrobertson.plugins.angularFileSwitcher.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.jaredrobertson.plugins.angularFileSwitcher.models.CloseBehavior;
import com.jaredrobertson.plugins.angularFileSwitcher.models.Grouping;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Supports storing the application settings in a persistent way.
 * The {@link State} and {@link Storage} annotations define the name of the data and the file name where
 * these persistent application settings are stored.
 */
@State(name = "com.jaredrobertson.plugins.angularFileSwitcher.settings.AppSettingsState", storages = @Storage("SdkSettingsPlugin.xml"))
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    private static final String DEFAULT_CLASS_FILE_EXTENSIONS = ".ts .js";
    private static final String DEFAULT_TEMPLATE_FILE_EXTENSIONS = ".html .php .pug .slim";
    private static final String DEFAULT_STYLE_FILE_EXTENSIONS = ".css .sass .scss .less .styl";
    private static final String DEFAULT_TEST_FILE_EXTENSIONS = ".test.js .test.ts .spec.js .spec.ts _spec.js _spec.ts";
    private static final Grouping DEFAULT_SWITCHER_GROUPING = Grouping.TAB_GROUP;
    private static final CloseBehavior DEFAULT_CLOSE_BEHAVIOR = CloseBehavior.ONLY_ON_ACTION;

    public String classFileExtensions = DEFAULT_CLASS_FILE_EXTENSIONS;
    public String templateFileExtensions = DEFAULT_TEMPLATE_FILE_EXTENSIONS;
    public String styleFileExtensions = DEFAULT_STYLE_FILE_EXTENSIONS;
    public String testFileExtensions = DEFAULT_TEST_FILE_EXTENSIONS;
    public Grouping grouping = DEFAULT_SWITCHER_GROUPING;
    public CloseBehavior closeBehavior = DEFAULT_CLOSE_BEHAVIOR;

    public static AppSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AppSettingsState.class);
    }

    @Nullable
    @Override
    public AppSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
