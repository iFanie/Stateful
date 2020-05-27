package dev.fanie.statefulapp

import dev.fanie.stateful.Stateful
import dev.fanie.stateful.StatefulType

@Stateful
data class Model(
    val title: String,
    val titleColor: Int,
    val titleFontSize: Float?,
    val isTitleVisible: Boolean,
    val titleAnimates: Boolean?
)

@Stateful(type = StatefulType.STACK)
data class AnotherModel(
    val index: Int,
    val model: Model
)