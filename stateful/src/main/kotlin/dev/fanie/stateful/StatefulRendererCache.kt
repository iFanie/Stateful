package dev.fanie.stateful

import dev.fanie.stateful.RendererConfigurationError.NO_MATCHING_RENDERERS_FOUND

/**
 * Creates a lazy initializer for retrieving new instances of the [StatefulRendererCache] type.
 * @param Model The type of model that the renderers access.
 * @param Property The subtype of [StatefulProperty] used to enumerate the [Model] properties.
 * @param listener The listener and owner of the renderers.
 * @param allowMissingRenderers When true, no error will be thrown when a renderer is not found for a given [Property].
 * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
 */
fun <Model : Any, Property : StatefulProperty<*, Model>> statefulRendererCache(
    listener: Any,
    allowMissingRenderers: Boolean,
    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
) = lazy(lazyMode) { StatefulRendererCacheImpl<Model, Property>(listener, allowMissingRenderers) }

/**
 * Signature for the caching mechanism for the renderer invocation functions. Use the [statefulRendererCache] function for
 * creating new instances.
 * @param Model The type of model that the renderers access.
 * @param Property The subtype of [StatefulProperty] used to enumerate the [Model] properties.
 */
interface StatefulRendererCache<Model : Any, Property : StatefulProperty<*, Model>> {
    fun getRenderers(property: Property): List<Function2<Model?, Model, Unit>>?
}

/**
 * Implementation of the [StatefulRendererCache] type. Use the [statefulRendererCache] function for creating new instances.
 * @param Model The type of model that the renderers access.
 * @param Property The subtype of [StatefulProperty] used to enumerate the [Model] properties.
 * @property listener The listener and owner of the renderers.
 * @property allowMissingRenderers When true, no error will be thrown when a renderer is not found for a given [Property].
 */
class StatefulRendererCacheImpl<Model : Any, Property : StatefulProperty<*, Model>> internal constructor(
    private val listener: Any,
    private val allowMissingRenderers: Boolean
) : StatefulRendererCache<Model, Property> {
    private val rendererCache = mutableMapOf<Property, List<Function2<Model?, Model, Unit>>?>()

    override fun getRenderers(property: Property): List<Function2<Model?, Model, Unit>>? =
        if (rendererCache.containsKey(property)) {
            rendererCache[property]
        } else {
            val renderers = fetchRenderersFromListener(property)
            rendererCache[property] = renderers
            renderers
        }

    private fun fetchRenderersFromListener(property: Property): List<Function2<Model?, Model, Unit>>? = try {
        buildPropertyRenderers(property, listener)
    } catch (configurationException: RendererConfigurationException) {
        if (configurationException.error == NO_MATCHING_RENDERERS_FOUND && allowMissingRenderers) {
            null
        } else {
            throw configurationException
        }
    }
}
