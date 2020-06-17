package dev.fanie.stateful.util

import dev.fanie.stateful.AbstractStatefulInstance
import dev.fanie.stateful.Renders
import dev.fanie.stateful.StatefulProperty
import dev.fanie.stateful.buildPropertyRenderers
import java.util.Objects
import kotlin.reflect.KClass

data class TestModel(val int: Int = 0, val strings: List<String>? = null)

internal class TestStateful(private val renderer: Any) : AbstractStatefulInstance<TestModel>() {
    override fun announce(currentInstance: TestModel?, newInstance: TestModel) {
        if (!Objects.equals(currentInstance?.int, newInstance.int)) {
            buildPropertyRenderers(Property.INT, renderer).forEach { it(currentInstance, newInstance) }
        }

        if (!Objects.equals(currentInstance?.strings, newInstance.strings)) {
            buildPropertyRenderers(Property.STRINGS, renderer).forEach { it(currentInstance, newInstance) }
        }
    }

    sealed class Property<Type : Any, Model : Any> : StatefulProperty<Type, Model> {
        object INT : Property<Int, TestModel>() {
            override val getter: Function1<TestModel, Int> = { model -> model.int }
            override val type: KClass<Int> = Int::class
            override val isOptional: Boolean = false
            override val modelType: KClass<TestModel> = TestModel::class
        }

        @Suppress("UNCHECKED_CAST")
        object STRINGS : Property<List<String>, TestModel>() {
            override val getter: Function1<TestModel, List<String>?> = { model -> model.strings }
            override val type: KClass<List<String>> = List::class as KClass<List<String>>
            override val isOptional: Boolean = true
            override val modelType: KClass<TestModel> = TestModel::class
        }
    }
}

class TestView {
    var renderedInt: Int? = null
    var renderedIntPairs = mutableListOf<Pair<Int?, Int>>()
    var renderedModel: TestModel? = null
    var renderedModelPairs = mutableListOf<Pair<TestModel?, TestModel>>()
    var renderedStrings = mutableListOf<List<String>>()

    fun reset() {
        renderedInt = null
        renderedIntPairs.clear()
        renderedModel = null
        renderedModelPairs.clear()
    }

    @Renders(TestStateful.Property.INT::class)
    fun renderInt(int: Int) {
        renderedInt = int
    }

    @Renders(TestStateful.Property.INT::class)
    fun renderIntPairs(current: Int?, new: Int) {
        renderedIntPairs.add(current to new)
    }

    @Renders(TestStateful.Property.INT::class)
    fun renderModel(model: TestModel) {
        renderedModel = model
    }

    @Renders(TestStateful.Property.INT::class)
    fun renderModelPairs(old: TestModel?, new: TestModel) {
        renderedModelPairs.add(old to new)
    }

    @Renders(TestStateful.Property.STRINGS::class)
    fun renderStrings(strings: List<String>?) {
        strings?.let { renderedStrings.add(it) }
    }
}

object ErrorTestViews {
    class NoRenderers

    class NoStrings {
        @Renders(TestStateful.Property.INT::class)
        fun renderInt(int: Int) {
            /* renders renders */
        }
    }

    class ThreeInts {
        @Renders(TestStateful.Property.INT::class)
        fun renderInt(int1: Int, int2: Int, int3: Int) {
            /* renders renders */
        }
    }

    class ShouldBeInt {
        @Renders(TestStateful.Property.INT::class)
        fun renderInt(int: String) {
            /* renders renders */
        }
    }

    class NonOptionalStrings {
        @Renders(TestStateful.Property.STRINGS::class)
        fun renderStrings(strings: List<String>) {

        }
    }

    class NonOptionalOld {
        @Renders(TestStateful.Property.INT::class)
        fun renderInt(old: Int, new: Int) {
            /* renders renders */
        }
    }

    class Different {
        @Renders(TestStateful.Property.INT::class)
        fun renderInt(old: Int, new: String) {
            /* renders renders */
        }
    }
}
