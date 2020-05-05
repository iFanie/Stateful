package dev.fanie.statefulcompiler

import java.io.File

class ClassGenerator(private val generationDirectory: File) {
    fun generate(classPackage: String, className: String, classSource: String): Boolean {
        return try {
            val classDirectory = File(generationDirectory, getRelativePath(classPackage))
            classDirectory.mkdirs()
            val classFile = File(classDirectory, getFileName(className))
            classFile.writeText(classSource)
            true
        } catch (ignored: Exception) {
            false
        }
    }

    private fun getRelativePath(classPackage: String) = classPackage.replace(".", "/")

    private fun getFileName(className: String) = "$className.kt"
}