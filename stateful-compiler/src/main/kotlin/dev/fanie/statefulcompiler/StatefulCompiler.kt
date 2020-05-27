package dev.fanie.statefulcompiler

import dev.fanie.stateful.Stateful
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.ElementFilter

class StatefulCompiler : AbstractProcessor() {
    override fun getSupportedOptions() = mutableSetOf(OPTION_GEN_PACKAGE)
    override fun getSupportedSourceVersion() = SourceVersion.RELEASE_8
    override fun getSupportedAnnotationTypes() = mutableSetOf(Stateful::class.java.canonicalName)

    lateinit var classGenerator: ClassGenerator
    lateinit var messager: Messager
    lateinit var processingUtils: ProcessingUtils

    override fun init(env: ProcessingEnvironment) {
        super.init(env)
        inject(this, env)
    }

    override fun process(annotations: MutableSet<out TypeElement>, env: RoundEnvironment): Boolean {
        processRound(env)
        return true
    }

    private fun processRound(env: RoundEnvironment) {
        ElementFilter.typesIn(env.getElementsAnnotatedWith(Stateful::class.java))
            .forEach { stateful ->
                val statefulPackage = processingUtils.getPackageOf(stateful).toString()
                val statefulClass = processingUtils.getClassOf(stateful)
                val statefulGetters = processingUtils.getGettersOf(stateful)

                val updateListenerBuilder =
                    UpdateListenerBuilder(statefulPackage, statefulClass, statefulGetters)
                classGenerator.generate(updateListenerBuilder)

                val statefulBuilder =
                    StatefulBuilder(statefulPackage, statefulClass, statefulGetters)
                classGenerator.generate(statefulBuilder)
            }
    }
}
