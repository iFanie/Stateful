package dev.fanie.statefulcompiler

import dev.fanie.statefulcompiler.util.processingEnvironment
import dev.fanie.statefulcompiler.util.statefulCompiler
import org.junit.Test

internal class InjectorKtTest {
    @Test
    fun `when injecting to a StatefulCompiler, then the expected class generator is present`() {
        val compiler = statefulCompiler()
        val environment = processingEnvironment()
        inject(compiler, environment)

        compiler.classGenerator.toString()
    }

    @Test
    fun `when injecting to a StatefulCompiler, then the expected messager is present`() {
        val compiler = statefulCompiler()
        val environment = processingEnvironment()
        inject(compiler, environment)

        compiler.messager.toString()
    }

    @Test
    fun `when injecting to a StatefulCompiler, then the processing utils are present`() {
        val compiler = statefulCompiler()
        val environment = processingEnvironment()
        inject(compiler, environment)

        compiler.processingUtils.toString()
    }
}
