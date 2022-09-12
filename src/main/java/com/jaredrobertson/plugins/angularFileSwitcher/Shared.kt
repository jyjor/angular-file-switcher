package com.jaredrobertson.plugins.angularFileSwitcher;

import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.jaredrobertson.plugins.angularFileSwitcher.settings.AppSettingsState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Shared {
    public static @Nullable String getNextPath(String path) {
        final String[] extensions = getAllExtensions();
        final int pathExtensionIndex = getExtensionIndex(path, extensions);
        if (pathExtensionIndex == -1) return null;

        final String basePath = getBasePath(path, pathExtensionIndex, extensions);

        for (int i = 1; i < extensions.length; i++) {
            final int index = (pathExtensionIndex + i) % extensions.length;
            final String newExtension = extensions[index];
            final String testPath = basePath + newExtension;
            final VirtualFile testFile = LocalFileSystem.getInstance().findFileByPath(testPath);
            if (testFile != null && testFile.exists()) {
                return testPath;
            }
        }
        return null;
    }

    public static @NotNull List<VirtualFile> getOtherFiles(String path) {
        final String[] extensions = getAllExtensions();
        final List<VirtualFile> files = new ArrayList<>(extensions.length - 1);
        final int pathExtensionIndex = getExtensionIndex(path, extensions);
        if (pathExtensionIndex == -1) {
            return files;
        }

        final String basePath = getBasePath(path, pathExtensionIndex, extensions);

        for (int i = 0; i < extensions.length; i++) {
            if (i == pathExtensionIndex) continue;
            VirtualFile file = getValidFile(basePath, i, extensions);
            if (file != null) {
                files.add(file);
            }
        }

        return files;
    }

    private static String[] getAllExtensions() {
        final AppSettingsState settings = AppSettingsState.getInstance();
        return (settings.classFileExtensions.trim() + " " +
                settings.templateFileExtensions.trim() + " " +
                settings.styleFileExtensions.trim() + " " +
                settings.testFileExtensions.trim()).split(" +");
    }

    private static @Nullable VirtualFile getValidFile(String basePath, int extensionIndex, String[] extensions) {
        final String extension = extensions[extensionIndex];
        final String path = basePath + extension;
        final VirtualFile file = LocalFileSystem.getInstance().findFileByPath(path);
        if (file == null || !file.exists()) return null;
        return file;
    }

    private static int getExtensionIndex(String path, String[] extensions) {
        String extension = "";
        int index = -1;
        for (int i = 0; i < extensions.length; i++) {
            if (path.endsWith(extensions[i])) {
                if (extensions[i].length() > extension.length()) {
                    // The path might match both ".ts" and ".spec.ts"
                    // Pick the longer extension because it's a more accurate match
                    extension = extensions[i];
                    index = i;
                }
            }
        }

        return index;
    }

    private static @Nullable String getBasePath(String path, int extensionIndex, String[] extensions) {
        if (extensionIndex == -1) return null;

        final int extensionLength = extensions[extensionIndex].length();
        final int baseLength = path.length() - extensionLength;
        return path.substring(0, baseLength);
    }
}
