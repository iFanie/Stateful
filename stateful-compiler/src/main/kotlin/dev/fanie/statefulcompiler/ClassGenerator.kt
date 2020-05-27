package dev.fanie.statefulcompiler

import java.io.File

class ClassGenerator(private val generationDirectory: File) {
    private fun generate(classPackage: String, className: String, classSource: String): Boolean {
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

    fun generate(classBuilder: ClassBuilder) =
        generate(classBuilder.classPackage, classBuilder.className, classBuilder.classSource)

    private fun getRelativePath(classPackage: String) = classPackage.replace(".", "/")

    private fun getFileName(className: String) = "$className.kt"
}
