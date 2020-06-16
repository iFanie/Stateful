package dev.fanie.stateful

import dev.fanie.stateful.RendererConfigurationError.DIFFERENT_RENDERER_PARAMETER_TYPES
import dev.fanie.stateful.RendererConfigurationError.INVALID_RENDERER_PARAMETERS
import dev.fanie.stateful.RendererConfigurationError.INVALID_RENDERER_PARAMETER_TYPE
import dev.fanie.stateful.RendererConfigurationError.NON_OPTIONAL_FIRST_RENDERER_PARAMETER
import dev.fanie.stateful.RendererConfigurationError.NO_MATCHING_RENDERERS_FOUND
import dev.fanie.stateful.RendererConfigurationError.NO_RENDERERS_FOUND
import dev.fanie.stateful.RendererConfigurationError.WRONG_RENDERER_PARAMETER_NULLABILITY
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions

/**
 * Describes a model class property which can be rendered by an appropriate listener instance.
 * @param Type The type of the property.
 * @param Model The model class and owner of the property.
 * @property getter Function that when invoked can fetch the property value from the property owner.
 * @property type The property type, as a [KClass] instance.
 * @property isOptional Describes whether the property is optional, marked as nullable, or not.
 * @property modelType The type of the model class, as a [KClass] instance.
 */
interface StatefulProperty<Type : Any, Model : Any> {
    val getter: Function1<Model, Type?>
    val type: KClass<Type>
    val isOptional: Boolean
    val modelType: KClass<Model>
}

/**
 * Decorates functions that can be invoked to render a specific property of a model class annotated with the [Stateful]
 * annotation. Uses the default retention type, which is [AnnotationRetention.RUNTIME].
 * <p>
 * A renderer function can contain one of the following configurations of parameters
 * <li> newValue of type of the property
 * <li> oldValue (as optional) and newValue of the type of the property
 * <li> newInstance of the type of the type of the model and owner of the property
 * <li> oldInstance (as optional) and newInstance of the
 * @property property The property, as a generated [KClass] instance of the [StatefulProperty] type, for which the annotated
 *   function will be invoked.
 */
@Target(AnnotationTarget.FUNCTION)
annotation class Renders(val property: KClass<out StatefulProperty<*, *>>)

/**
 * Enumerates the different configuration mistake that can occur when creating a [Renders] annotated renderer function.
 * @property messageFormat A string format for displaying a readable error message.
 */
enum class RendererConfigurationError(val messageFormat: String) {
    NO_RENDERERS_FOUND("NO_RENDERERS_FOUND: No functions annotated with @Renders found in `%s`."),
    NO_MATCHING_RENDERERS_FOUND("NO_MATCHING_RENDERERS_FOUND: No functions annotated with @Renders for `%s` found in `%s`."),
    INVALID_RENDERER_PARAMETERS("INVALID_RENDERER_PARAMETERS: Function `%s` should contain one of the following combinations of parameters: (newValue), (oldValue?, newValue), (newModel), (oldModel?, newModel)."),
    INVALID_RENDERER_PARAMETER_TYPE("INVALID_RENDERER_PARAMETER_TYPE: Parameter `%s` of function `%s` should either be of `%s` or `%s` type."),
    WRONG_RENDERER_PARAMETER_NULLABILITY("WRONG_RENDERER_PARAMETER_NULLABILITY: Parameter `%s` of function `%s` should be (optional = %s)."),
    NON_OPTIONAL_FIRST_RENDERER_PARAMETER("NON_OPTIONAL_FIRST_RENDERER_PARAMETER: Parameter `%s` of function `%s` should be optional."),
    DIFFERENT_RENDERER_PARAMETER_TYPES("DIFFERENT_RENDERER_PARAMETER_TYPES: Parameters `%s` and `%s` of function `%s` should be of the same type.")
}

/**
 * Thrown by the [invokePropertyRenderers] when a [Renders] annotated function has been wrongly configured.
 * @property error The error type, of the [RendererConfigurationError] type.
 * @param messageFormatArgs The string format arguments for displaying a readable error message.
 */
class RendererConfigurationException(val error: RendererConfigurationError, vararg messageFormatArgs: Any) :
    RuntimeException(error.messageFormat.format(*messageFormatArgs))

/**
 * Invokes a render function for a property update of a given Model instance.
 * @param Type The type of the property.
 * @param Model The model class and owner of the property.
 * @param property The [StatefulProperty] implementation for the specific property.
 * @param listener The instance containing [Renders] annotated functions to be invoked.
 * @param currentInstance The currently rendered instance of the [Model] type, if any.
 * @param newInstance The instance of the [Model] type to be rendered.
 * @throws [RendererConfigurationException] When renderer function has not been properly set-up. See [Renders] for how to
 *   setup a renderer function properly.
 */
fun <Type : Any, Model : Any> invokePropertyRenderers(
    property: StatefulProperty<Type, Model>,
    listener: Any,
    currentInstance: Model?,
    newInstance: Model
) {
    val renderers = listener::class.functions
        .map { it to it.findAnnotation<Renders>() }
        .filter { it.second?.let { renders -> renders.property.objectInstance != null } ?: false }
    if (renderers.isEmpty()) {
        throw RendererConfigurationException(NO_RENDERERS_FOUND, listener)
    }

    val matchingRenderers = renderers.filter { requireNotNull(it.second).property.objectInstance == property }
    if (matchingRenderers.isEmpty()) {
        throw RendererConfigurationException(NO_MATCHING_RENDERERS_FOUND, property, listener)
    }

    matchingRenderers.forEach { (renderer, _) ->
        invokePropertyRenderer(property, listener, renderer, currentInstance, newInstance)
    }
}

private fun <Type : Any, Model : Any> invokePropertyRenderer(
    property: StatefulProperty<Type, Model>,
    listener: Any,
    renderer: KFunction<*>,
    currentInstance: Model?,
    newInstance: Model
) {
    val parameters = renderer.parameters
    if (parameters.size < 2 || parameters.size > 3) {
        throw RendererConfigurationException(INVALID_RENDERER_PARAMETERS, renderer)
    }

    val firstParameter = parameters[1]
    if (!property.validateParameterType(firstParameter)) {
        throw RendererConfigurationException(
            INVALID_RENDERER_PARAMETER_TYPE,
            firstParameter.type,
            renderer,
            property.type,
            property.modelType
        )
    }

    if (parameters.size == 2) {
        if (firstParameter.type.isMarkedNullable != property.isOptional) {
            throw RendererConfigurationException(
                WRONG_RENDERER_PARAMETER_NULLABILITY,
                firstParameter,
                renderer,
                property.isOptional
            )
        }

        val value = property.valueOf(firstParameter, newInstance)
        renderer.call(listener, value)
    } else {
        if (!firstParameter.type.isMarkedNullable) {
            throw RendererConfigurationException(NON_OPTIONAL_FIRST_RENDERER_PARAMETER, firstParameter, renderer)
        }

        val secondParameter = parameters[2]
        if (secondParameter.type.classifier != firstParameter.type.classifier) {
            throw RendererConfigurationException(
                DIFFERENT_RENDERER_PARAMETER_TYPES,
                firstParameter,
                secondParameter,
                renderer
            )
        }

        if (secondParameter.type.isMarkedNullable != property.isOptional) {
            throw RendererConfigurationException(
                WRONG_RENDERER_PARAMETER_NULLABILITY,
                secondParameter,
                renderer,
                property.isOptional
            )
        }

        val firstValue = property.valueOf(firstParameter, currentInstance)
        val secondValue = property.valueOf(secondParameter, newInstance)
        renderer.call(listener, firstValue, secondValue)
    }
}

private fun StatefulProperty<*, *>.validateParameterType(parameter: KParameter): Boolean =
    parameter.type.classifier in listOf(this.type, this.modelType)

private fun <Type : Any, Model : Any> StatefulProperty<Type, Model>.valueOf(parameter: KParameter, instance: Model?): Any? =
    if (parameter.type.classifier == this.modelType) {
        instance
    } else {
        instance?.let { this.getter(it) }
    }

