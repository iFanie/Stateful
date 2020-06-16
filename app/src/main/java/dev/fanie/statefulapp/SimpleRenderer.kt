package dev.fanie.statefulapp

import dev.fanie.statefulapp.stateful.simple.StatefulSimpleListener
import dev.fanie.statefulapp.stateful.simple.stateful

class SimpleRenderer : View<Simple>, StatefulSimpleListener {
    private val statefulSimple by stateful()

    override fun render(model: Simple) {
        statefulSimple.accept(model)
    }

    override fun onNumberUpdated(currentNumber: Int?, newNumber: Int) {
        /* Renders the number update */
    }
}
