package dev.fanie.statefulapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.fanie.stateful.Renders
import dev.fanie.statefulapp.stateful.model.StatefulModel
import dev.fanie.statefulapp.stateful.model.stateful

class ModelRenderer : AppCompatActivity(), View<Model> {
    private val statefulModel by stateful()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun render(model: Model) {
        statefulModel.accept(model)
    }

    @Renders(StatefulModel.Property.TITLE::class)
    fun renderTitle(title: String) {
        /* Renders the new title */
    }
}
