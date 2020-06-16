package dev.fanie.statefulapp

interface View<Model : Any> {
    fun render(model: Model)
}
