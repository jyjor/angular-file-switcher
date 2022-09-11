package com.jaredrobertson.plugins.angularFileSwitcher;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.FileEditorManagerImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.jaredrobertson.plugins.angularFileSwitcher.models.CloseBehavior;
import com.jaredrobertson.plugins.angularFileSwitcher.models.Grouping;
import com.jaredrobertson.plugins.angularFileSwitcher.settings.AppSettingsState;

import java.util.List;
import java.util.stream.Collectors;

public class FileSwitchAction extends AnAction {
    public FileSwitchAction() {
        super("Open Next Similarly Named File");
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        final DataContext dataContext = event.getDataContext();
        final VirtualFile currentFile = CommonDataKeys.VIRTUAL_FILE.getData(dataContext);
        if (currentFile == null) return;

        final String currentPath = currentFile.getCanonicalPath();
        if (currentPath == null) return;

        final String newPath = Shared.getNextPath(currentPath);
        if (newPath == null) return;

        if (currentPath.equals(newPath)) return;

        final VirtualFile newFile = LocalFileSystem.getInstance().findFileByPath(newPath);
        if (newFile == null || !newFile.exists()) return;

        final Project project = CommonDataKeys.PROJECT.getData(dataContext);
        if (project == null) return;

        if (AppSettingsState.getInstance().grouping == Grouping.EVERYWHERE) {
            switchFileEverywhere(project, newFile);
        } else {
            switchFileInEditorGroup(dataContext, project, newFile);
        }
    }

    private void switchFileEverywhere(Project project, VirtualFile newFile) {
        new OpenFileDescriptor(project, newFile).navigate(true);

        if (AppSettingsState.getInstance().closeBehavior == CloseBehavior.ONLY_ON_ACTION) {
            final FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
            List<VirtualFile> otherFiles = Shared.getOtherFiles(newFile.getCanonicalPath());
            List<VirtualFile> otherOpenFiles = otherFiles.stream().filter(fileEditorManager::isFileOpen).collect(Collectors.toList());
            otherOpenFiles.forEach(fileEditorManager::closeFile);
        }
    }

    private void switchFileInEditorGroup(DataContext dataContext, Project project, VirtualFile newFile) {
        final EditorWindow window = EditorWindow.DATA_KEY.getData(dataContext);
        if (window == null) return;

        ((FileEditorManagerImpl) FileEditorManagerEx.getInstanceEx(project)).openFileImpl2(window, newFile, true);

        if (AppSettingsState.getInstance().closeBehavior == CloseBehavior.ONLY_ON_ACTION) {
            List<VirtualFile> otherFiles = Shared.getOtherFiles(newFile.getCanonicalPath());
            List<VirtualFile> otherOpenFiles = otherFiles.stream().filter(window::isFileOpen).collect(Collectors.toList());
            otherOpenFiles.forEach(window::closeFile);
        }
    }
}
