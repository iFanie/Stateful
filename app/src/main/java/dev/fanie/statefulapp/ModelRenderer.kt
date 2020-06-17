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

    @Renders(StatefulModel.Property.TITLE_COLOR::class)
    fun renderTitleColor(color: Int) {
        /* Renders the new title color */
    }

    @Renders(StatefulModel.Property.TITLE_FONT_SIZE::class)
    fun renderTitleSize(fontSize: Float?) {
        /* Renders the new title font size */
    }

    @Renders(StatefulModel.Property.IS_TITLE_VISIBLE::class)
    fun renderTitleVisibility(isVisible: Boolean) {
        /* Renders the new title visibility */
    }

    @Renders(StatefulModel.Property.TITLE_ANIMATES::class)
    fun renderTitleAnimation(animates: Boolean?) {
        /* Renders the new title animation */
    }
}
