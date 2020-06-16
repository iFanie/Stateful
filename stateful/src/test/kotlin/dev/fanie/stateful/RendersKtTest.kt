package dev.fanie.stateful

import dev.fanie.stateful.util.ErrorTestViews
import dev.fanie.stateful.util.TestModel
import dev.fanie.stateful.util.TestStateful
import dev.fanie.stateful.util.TestView
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class RendersKtTest {
    @Test(expected = RendererConfigurationException::class)
    fun `when the listener contains no renderers, then exception is thrown`() {
        val view = ErrorTestViews.NoRenderers()
        val stateful = TestStateful(view)
        stateful.accept(TestModel(1))
    }

    @Test(expected = RendererConfigurationException::class)
    fun `when the listener contains no matching renderers, then exception is thrown`() {
        val view = ErrorTestViews.NoStrings()
        val stateful = TestStateful(view)
        stateful.accept(TestModel(strings = listOf("hello", "world")))
    }

    @Test(expected = RendererConfigurationException::class)
    fun `when the listener contains a renderer with an invalid number of parameters, then exception is thrown`() {
        val view = ErrorTestViews.ThreeInts()
        val stateful = TestStateful(view)
        stateful.accept(TestModel(1))
    }

    @Test(expected = RendererConfigurationException::class)
    fun `when the listener contains a renderer with an invalid type of parameter, then exception is thrown`() {
        val view = ErrorTestViews.ShouldBeInt()
        val stateful = TestStateful(view)
        stateful.accept(TestModel(1))
    }

    @Test(expected = RendererConfigurationException::class)
    fun `when the listener contains a renderer with a wrong nullability parameter, then exception is thrown`() {
        val view = ErrorTestViews.NonOptionalStrings()
        val stateful = TestStateful(view)
        stateful.accept(TestModel(1))
    }

    @Test(expected = RendererConfigurationException::class)
    fun `when the listener contains a renderer with a non-optional old value parameter, then exception is thrown`() {
        val view = ErrorTestViews.NonOptionalOld()
        val stateful = TestStateful(view)
        stateful.accept(TestModel(1))
    }

    @Test(expected = RendererConfigurationException::class)
    fun `when the listener contains a renderer with different types of parameters, then exception is thrown`() {
        val view = ErrorTestViews.Different()
        val stateful = TestStateful(view)
        stateful.accept(TestModel(1))
    }

    private val view = TestView()
    private val stateful = TestStateful(view)

    @Before
    fun setUp() {
        view.reset()
    }

    @Test
    fun `when rendering a value, then the value is rendered`() {
        stateful.accept(TestModel(1))
        assertEquals(1, view.renderedInt)
    }

    @Test
    fun `when rendering a pair of old and new values, then the values are rendered`() {
        stateful.accept(TestModel(1))
        assertEquals(null to 1, view.renderedIntPairs.last())
        stateful.accept(TestModel(2))
        assertEquals(1 to 2, view.renderedIntPairs.last())
    }

    @Test
    fun `when rendering a model, then the model is rendered`() {
        val model = TestModel(1)
        stateful.accept(model)
        assertEquals(model, view.renderedModel)
    }

    @Test
    fun `when rendering a pair of old and new models, then the models are rendered`() {
        val model1 = TestModel(1)
        val model2 = TestModel(2)
        stateful.accept(model1)
        assertEquals(null to model1, view.renderedModelPairs.last())
        stateful.accept(model2)
        assertEquals(model1 to model2, view.renderedModelPairs.last())
    }

    @Test
    fun `when rendering a parameterized type value, then the value is rendered`() {
        val strings = listOf("hello", "world")
        val model = TestModel(strings = strings)
        stateful.accept(model)
        assertEquals(strings, view.renderedStrings.last())
    }
}
