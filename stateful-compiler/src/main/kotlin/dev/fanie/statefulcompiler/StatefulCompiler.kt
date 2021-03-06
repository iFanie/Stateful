package dev.fanie.statefulcompiler

import dev.fanie.stateful.Stateful
import dev.fanie.stateful.StatefulOptions
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

                val annotation = stateful.getAnnotation(Stateful::class.java)
                val statefulOptions = annotation.options.toSet()
                val withListener = statefulOptions.contains(StatefulOptions.WITH_LISTENER) ||
                        statefulOptions.contains(StatefulOptions.WITH_NON_CASCADING_LISTENER)

                if (withListener) {
                    val updateListenerBuilder = ListenerBuilder(
                        statefulPackage,
                        statefulClass,
                        statefulGetters,
                        statefulOptions.contains(StatefulOptions.WITH_NON_CASCADING_LISTENER),
                        statefulOptions.contains(StatefulOptions.NO_DIFFING)
                    )
                    classGenerator.generate(updateListenerBuilder)
                }

                val statefulType = annotation.type
                val statefulBuilder = WrapperBuilder(
                    statefulPackage,
                    statefulClass,
                    statefulGetters,
                    statefulType,
                    statefulOptions.contains(StatefulOptions.NO_LAZY_INIT),
                    statefulOptions.contains(StatefulOptions.NO_DIFFING),
                    withListener,
                    statefulOptions.contains(StatefulOptions.ALLOW_MISSING_RENDERERS)
                )
                classGenerator.generate(statefulBuilder)
            }
    }
}
