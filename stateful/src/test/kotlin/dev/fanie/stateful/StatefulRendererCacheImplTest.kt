package dev.fanie.stateful

import dev.fanie.stateful.util.ErrorTestViews
import dev.fanie.stateful.util.TestModel
import dev.fanie.stateful.util.TestStateful
import dev.fanie.stateful.util.TestView
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import org.junit.Test

internal class StatefulRendererCacheImplTest {
    @Test
    fun `given renderer exists, when getting the renderer invocation functions, then the result is the expected`() {
        val view = TestView()
        val cache = statefulRendererCache<TestModel, TestStateful.Property<*, TestModel>>(view, false).value
        val renderers = cache.getRenderers(TestStateful.Property.INT)
        assertNotNull(renderers)

        val cached = cache.getRenderers(TestStateful.Property.INT)
        assertEquals(renderers, cached)
    }

    @Test(expected = RendererConfigurationException::class)
    fun `given renderer does not exist and missing renderers are not allowed, when getting the renderer invocation functions, then exception is thrown`() {
        val view = ErrorTestViews.NoStrings()
        val cache = statefulRendererCache<TestModel, TestStateful.Property<*, TestModel>>(view, false).value
        val renderers = cache.getRenderers(TestStateful.Property.STRINGS)
        assertNotNull(renderers)

        val cached = cache.getRenderers(TestStateful.Property.INT)
        assertEquals(renderers, cached)
    }

    @Test
    fun `given renderer does not exist and missing renderers are allowed, when getting the renderer invocation functions, then the result is null`() {
        val view = ErrorTestViews.NoStrings()
        val cache = statefulRendererCache<TestModel, TestStateful.Property<*, TestModel>>(view, true).value
        assertNull(cache.getRenderers(TestStateful.Property.STRINGS))
    }
}
