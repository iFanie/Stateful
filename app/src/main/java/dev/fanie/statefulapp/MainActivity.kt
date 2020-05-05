package dev.fanie.statefulapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.fanie.statefulapp.stateful.ModelUpdateListener
import dev.fanie.statefulapp.stateful.StatefulModel

class MainActivity : AppCompatActivity(), View, ModelUpdateListener {
    private val statefulModel = StatefulModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun render(model: Model) {
        statefulModel.accept(model)
    }

    override fun onTitleUpdated(newTitle: String) {

    }
}
