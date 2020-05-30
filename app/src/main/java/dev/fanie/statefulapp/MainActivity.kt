package dev.fanie.statefulapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.fanie.statefulapp.stateful.StatefulModelListener
import dev.fanie.statefulapp.stateful.stateful

class MainActivity : AppCompatActivity(), View, StatefulModelListener {
    private val statefulModel by stateful()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun render(model: Model) {
        statefulModel.accept(model)
    }

    override fun onTitleUpdated(newTitle: String) {
        /* render the new title */
    }
}
