package dev.fanie.statefulapp

import dev.fanie.stateful.Stateful

@Stateful
data class Model(
    val title: String,
    val titleColor: Int,
    val titleFontSize: Float?,
    val isTitleVisible: Boolean,
    val titleAnimates: Boolean?
)