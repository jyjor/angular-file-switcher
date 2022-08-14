package com.jaredrobertson.plugins.angularFileSwitcher;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.PsiAwareFileEditorManagerImpl;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class FileSwitchListener implements FileEditorManagerListener {
    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        FileEditorManagerListener.super.fileOpened(source, file);

        if (!Shared.shouldCloseAlways()) return;

        List<VirtualFile> otherFiles = Shared.getOtherFiles(file.getCanonicalPath());

        if (Shared.isSwitcherGroupingEverywhere()) {
            closeFilesEverywhere(source, otherFiles);
        } else {
            closeFilesInEditorGroup(source, otherFiles);
        }
    }

    private void closeFilesEverywhere(FileEditorManager source, List<VirtualFile> otherFiles) {
        List<VirtualFile> otherOpenFiles = otherFiles.stream().filter(source::isFileOpen).collect(Collectors.toList());
        otherOpenFiles.forEach(source::closeFile);
    }

    private void closeFilesInEditorGroup(FileEditorManager source, List<VirtualFile> otherFiles) {
        final EditorWindow window = ((PsiAwareFileEditorManagerImpl) source).getSplitters().getCurrentWindow();
        if (window == null) return;

        // Close all the files
        // This closeFile overload also removes the files from the selection history.
        // - This way "Reopen closed tab" works doesn't get stuck in a loop reopening the last two tabs
        // - In other words, the file group acts more like a single unit, rather than individual files
        List<VirtualFile> otherOpenFiles = otherFiles.stream().filter(window::isFileOpen).collect(Collectors.toList());
        otherOpenFiles.forEach(window::closeFile);
//        otherOpenFiles.forEach(file -> window.getManager().closeFile(file, window, true));
        otherOpenFiles.forEach(file -> window.getManager().getSelectionHistory().remove(Pair.create(file, window)));
    }
}
