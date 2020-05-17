package dev.fanie.statefulcompiler

import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File

class ClassGeneratorTest {
    private val rootDir = File("class-generator-test")
    private lateinit var underTest: ClassGenerator

    @Before
    fun setUp() {
        underTest = ClassGenerator(rootDir)
    }

    @After
    fun cleanUp() {
        rootDir.deleteRecursively()
    }

    @Test
    fun `when generating a classBuilder, then the expected file is created`() {
        val classPackage = "test.generation"
        val className = "TestClass"
        val input =
            classBuilder(classPackage, className, "package $classPackage; class $className {}")

        underTest.generate(input)

        val expectedFile = File(rootDir, "test/generation/TestClass.kt")
        assertTrue(expectedFile.isFile)
    }
}

private fun classBuilder(
    classPackage: String = "test",
    className: String = "test",
    classSource: String = "test"
) =
    object : ClassBuilder {
        override val classPackage: String = classPackage
        override val className: String = className
        override val classSource: String = classSource
    }