package com.jaredrobertson.plugins.angularFileSwitcher

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.fileEditor.impl.EditorWindow
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.jaredrobertson.plugins.angularFileSwitcher.models.CloseBehavior
import com.jaredrobertson.plugins.angularFileSwitcher.models.Grouping
import com.jaredrobertson.plugins.angularFileSwitcher.settings.AppSettingsState.Companion.instance
import java.util.function.Consumer
import java.util.stream.Collectors

abstract class TypedFileSwitchAction(actionName: String) : AnAction(actionName) {

    protected abstract fun getTargetExtension(): String

    override fun actionPerformed(event: AnActionEvent) {
        val dataContext = event.dataContext
        val currentFile = CommonDataKeys.VIRTUAL_FILE.getData(dataContext) ?: return
        val currentPath = currentFile.canonicalPath ?: return
        
        val targetExtension = getTargetExtension()
        val newPath = getPathWithExtension(currentPath, targetExtension) ?: return
        
        if (currentPath == newPath) return
        val newFile = LocalFileSystem.getInstance().findFileByPath(newPath)
        if (newFile == null || !newFile.exists()) return
        val project = CommonDataKeys.PROJECT.getData(dataContext) ?: return
        
        if (instance.grouping === Grouping.EVERYWHERE) {
            switchFileEverywhere(project, newFile)
        } else {
            switchFileInEditorGroup(dataContext, project, newFile)
        }
    }

    private fun getPathWithExtension(currentPath: String, targetExtension: String): String? {
        val extensions = Shared.allExtensions
        val pathExtensionIndex = Shared.getExtensionIndex(currentPath, extensions)
        if (pathExtensionIndex == -1) return null
        
        val basePath = Shared.getBasePath(currentPath, pathExtensionIndex, extensions)
        val targetPath = basePath + targetExtension
        
        val targetFile = LocalFileSystem.getInstance().findFileByPath(targetPath)
        return if (targetFile != null && targetFile.exists()) targetPath else null
    }

    private fun switchFileEverywhere(project: Project, newFile: VirtualFile) {
        OpenFileDescriptor(project, newFile).navigate(true)
        if (instance.closeBehavior === CloseBehavior.ONLY_ON_ACTION) {
            val fileEditorManager = FileEditorManager.getInstance(project)
            val path = newFile.canonicalPath ?: return
            val otherFiles = Shared.getOtherFiles(path)
            val otherOpenFiles = otherFiles.stream()
                .filter { file: VirtualFile? -> fileEditorManager.isFileOpen(file!!) }
                .collect(Collectors.toList())
            otherOpenFiles.forEach(Consumer { file: VirtualFile? ->
                fileEditorManager.closeFile(
                    file!!
                )
            })
        }
    }

    private fun switchFileInEditorGroup(
        dataContext: DataContext,
        project: Project,
        newFile: VirtualFile
    ) {
        val window = EditorWindow.DATA_KEY.getData(dataContext)
            ?: return
        FileEditorManagerEx.getInstanceEx(project).openFileWithProviders(newFile, true, window)
        if (instance.closeBehavior === CloseBehavior.ONLY_ON_ACTION) {
            val path = newFile.canonicalPath ?: return
            val otherFiles = Shared.getOtherFiles(path)
            val otherOpenFiles = otherFiles.stream().filter { file: VirtualFile? ->
                window.isFileOpen(
                    file!!
                )
            }.collect(Collectors.toList())
            otherOpenFiles.forEach(Consumer { file: VirtualFile? ->
                window.closeFile(
                    file!!
                )
            })
        }
    }
} 