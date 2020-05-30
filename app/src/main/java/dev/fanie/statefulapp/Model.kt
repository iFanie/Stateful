package dev.fanie.statefulapp

import dev.fanie.stateful.Stateful
import dev.fanie.stateful.StatefulExtra
import dev.fanie.stateful.StatefulType

@Stateful(extras = [StatefulExtra.LAZY_INIT])
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

@Stateful
data class Complex(
    val model: Model,
    val models: List<Model>,
    val anotherModel: AnotherModel,
    val otherModels: Array<AnotherModel>,
    val ids: List<String>,
    val strs: Array<String>,
    val strMatrix: Array<Array<String>>,
    val ints: IntArray,
    val intMatrix: Array<IntArray>
)