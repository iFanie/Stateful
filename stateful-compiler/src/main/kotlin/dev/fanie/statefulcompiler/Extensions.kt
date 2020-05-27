package dev.fanie.statefulcompiler

import javax.lang.model.element.ExecutableElement

val ExecutableElement.name
    get() =
        propertyFromGetterName(this.simpleName.toString()).decapitalize()

private fun propertyFromGetterName(getterName: String) =
    if (getterName.startsWith(GETTER_PREFIX)) {
        getterName.replaceFirst(GETTER_PREFIX, "")
    } else {
        getterName
    }

val ExecutableElement.type get() = TypeMatcher.toKotlinType(this)