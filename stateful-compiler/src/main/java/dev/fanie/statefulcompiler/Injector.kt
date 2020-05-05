package dev.fanie.statefulcompiler

import java.io.File
import javax.annotation.processing.ProcessingEnvironment

fun inject(instance: StatefulCompiler, env: ProcessingEnvironment) {
    val generationDirectory = File(env.options[OPTION_GEN_PACKAGE] ?: "")
    val classGenerator = ClassGenerator(generationDirectory)
    instance.classGenerator = classGenerator
    instance.messager = env.messager
    val processingUtils = ProcessingUtils(env.elementUtils)
    instance.processingUtils = processingUtils
}