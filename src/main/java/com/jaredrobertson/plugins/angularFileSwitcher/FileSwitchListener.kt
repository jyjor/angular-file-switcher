package com.jaredrobertson.plugins.angularFileSwitcher

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.fileEditor.impl.PsiAwareFileEditorManagerImpl
import com.intellij.openapi.vfs.VirtualFile
import com.jaredrobertson.plugins.angularFileSwitcher.models.CloseBehavior
import com.jaredrobertson.plugins.angularFileSwitcher.models.Grouping
import com.jaredrobertson.plugins.angularFileSwitcher.settings.AppSettingsState.Companion.instance
import java.util.function.Consumer
import java.util.stream.Collectors

class FileSwitchListener : FileEditorManagerListener {
    override fun fileOpened(source: FileEditorManager, file: VirtualFile) {
        super.fileOpened(source, file)
        if (instance.closeBehavior !== CloseBehavior.ALWAYS) return
        val path = file.canonicalPath ?: return
        val otherFiles = Shared.getOtherFiles(path)
        if (instance.grouping === Grouping.EVERYWHERE) {
            closeFilesEverywhere(source, otherFiles)
        } else {
            closeFilesInEditorGroup(source, otherFiles)
        }
    }

    private fun closeFilesEverywhere(source: FileEditorManager, otherFiles: List<VirtualFile>) {
        val otherOpenFiles = otherFiles.stream().filter { file: VirtualFile? ->
            source.isFileOpen(
                file!!
            )
        }.collect(Collectors.toList())
        otherOpenFiles.forEach(Consumer { file: VirtualFile? ->
            source.closeFile(
                file!!
            )
        })
    }

    private fun closeFilesInEditorGroup(source: FileEditorManager, matchingFiles: List<VirtualFile>) {
        val window = (source as PsiAwareFileEditorManagerImpl).splitters.currentWindow ?: return

        // Close all the matching files in the splitter window
        val otherOpenFiles = matchingFiles.stream().filter { file: VirtualFile? ->
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