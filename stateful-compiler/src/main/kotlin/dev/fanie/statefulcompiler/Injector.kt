package dev.fanie.statefulcompiler

import java.io.File
import javax.annotation.processing.ProcessingEnvironment

fun inject(instance: StatefulCompiler, env: ProcessingEnvironment) {
    injectClassGenerator(env, instance)
    injectMessager(instance, env)
    injectProcessingUtils(env, instance)
}

private fun injectProcessingUtils(
    env: ProcessingEnvironment,
    instance: StatefulCompiler
) {
    val processingUtils = ProcessingUtils(env.elementUtils)
    instance.processingUtils = processingUtils
}

private fun injectMessager(
    instance: StatefulCompiler,
    env: ProcessingEnvironment
) {
    instance.messager = env.messager
}

private fun injectClassGenerator(
    env: ProcessingEnvironment,
    instance: StatefulCompiler
) {
    val generationDirectory = File(env.options[OPTION_GEN_PACKAGE] ?: "")
    val classGenerator = ClassGenerator(generationDirectory)
    instance.classGenerator = classGenerator
}