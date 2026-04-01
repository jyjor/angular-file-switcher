package com.jaredrobertson.plugins.angularFileSwitcher

import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.jaredrobertson.plugins.angularFileSwitcher.settings.AppSettingsState.Companion.instance

object Shared {
    fun getNextPath(path: String): String? {
        val extensions = allExtensions
        val pathExtensionIndex = getExtensionIndex(path, extensions)
        if (pathExtensionIndex == -1) return null
        val basePath = getBasePath(path, pathExtensionIndex, extensions)
        for (i in 1 until extensions.size) {
            val index = (pathExtensionIndex + i) % extensions.size
            val newExtension = extensions[index]
            val testPath = basePath + newExtension
            val testFile = LocalFileSystem.getInstance().findFileByPath(testPath)
            if (testFile != null && testFile.exists()) {
                return testPath
            }
        }
        return null
    }

    fun getOtherFiles(path: String): List<VirtualFile> {
        val extensions = allExtensions
        val files: MutableList<VirtualFile> = ArrayList(extensions.size - 1)
        val pathExtensionIndex = getExtensionIndex(path, extensions)
        if (pathExtensionIndex == -1) {
            return files
        }
        val basePath = getBasePath(path, pathExtensionIndex, extensions)
        for (i in extensions.indices) {
            if (i == pathExtensionIndex) continue
            val file = getValidFile(basePath, i, extensions)
            if (file != null) {
                files.add(file)
            }
        }
        return files
    }

    val allExtensions: Array<String>
        get() {
            val settings = instance
            return (settings.classFileExtensions.trim { it <= ' ' } + " " +
                    settings.templateFileExtensions.trim { it <= ' ' } + " " +
                    settings.styleFileExtensions.trim { it <= ' ' } + " " +
                    settings.testFileExtensions.trim { it <= ' ' }).split(" +".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
        }

    private fun getValidFile(
        basePath: String?,
        extensionIndex: Int,
        extensions: Array<String>
    ): VirtualFile? {
        val extension = extensions[extensionIndex]
        val path = basePath + extension
        val file = LocalFileSystem.getInstance().findFileByPath(path)
        return if (file == null || !file.exists()) null else file
    }

    fun getExtensionIndex(path: String, extensions: Array<String>): Int {
        var extension = ""
        var index = -1
        for (i in extensions.indices) {
            if (path.endsWith(extensions[i])) {
                if (extensions[i].length > extension.length) {
                    // The path might match both ".ts" and ".spec.ts"
                    // Pick the longer extension because it's a more accurate match
                    extension = extensions[i]
                    index = i
                }
            }
        }
        return index
    }

    fun getBasePath(path: String, extensionIndex: Int, extensions: Array<String>): String? {
        if (extensionIndex == -1) return null
        val extensionLength = extensions[extensionIndex].length
        val baseLength = path.length - extensionLength
        return path.substring(0, baseLength)
    }
}