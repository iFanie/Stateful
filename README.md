# Stateful
Annotation processor library that generates a stateful wrapper and an Update listener interface for
model classes.

### Example
You have a simple Model and a View that renders the model.
```kotlin
data class Model(
    val title: String,
    val titleFontSize: Float?,
    val isTitleVisible: Boolean
)

class View {
    fun render(model: Model) {
        /* renders the model */
    }
}
```

Instead rendering everything on every update or checking the updates manually
- Annotate the Model
```kotlin
@Stateful
data class Model( ... )
```

- Build. For the above model, the processor will generate the following classes
```kotlin
interface ModelUpdateListener {
    fun onTitleUpdated(newTitle: String) {}
    fun onTitleUpdated(oldTitle: String?, newTitle: String) {}
    fun onTitleUpdated(newModel: Model) {}
    fun onTitleUpdated(oldModel: Model?, newModel: Model) {}

    fun onTitleFontSizeUpdated(newTitleFontSize: Float?) {}
    fun onTitleFontSizeUpdated(oldTitleFontSize: Float?, newTitleFontSize: Float?) {}
    fun onTitleFontSizeUpdated(newModel: Model) {}
    fun onTitleFontSizeUpdated(oldModel: Model?, newModel: Model) {}

    fun onIsTitleVisibleUpdated(newIsTitleVisible: Boolean) {}
    fun onIsTitleVisibleUpdated(oldIsTitleVisible: Boolean?, newIsTitleVisible: Boolean) {}
    fun onIsTitleVisibleUpdated(newModel: Model) {}
    fun onIsTitleVisibleUpdated(oldModel: Model?, newModel: Model) {}
}

class StatefulModel(
    private val modelUpdateListener: ModelUpdateListener,
    initialModel: Model? = null
) {
    private var currentModel = initialModel

    fun accept(newModel: Model) {
        if (!Objects.equals(currentModel?.title, newModel.title)) {
            modelUpdateListener.onTitleUpdated(newModel.title)
            modelUpdateListener.onTitleUpdated(currentModel?.title, newModel.title)
            modelUpdateListener.onTitleUpdated(newModel)
            modelUpdateListener.onTitleUpdated(currentModel, newModel)
        }

        if (!Objects.equals(currentModel?.titleFontSize, newModel.titleFontSize)) {
            modelUpdateListener.onTitleFontSizeUpdated(newModel.titleFontSize)
            modelUpdateListener.onTitleFontSizeUpdated(currentModel?.titleFontSize, newModel.titleFontSize)
            modelUpdateListener.onTitleFontSizeUpdated(newModel)
            modelUpdateListener.onTitleFontSizeUpdated(currentModel, newModel)
        }

        if (!Objects.equals(currentModel?.isTitleVisible, newModel.isTitleVisible)) {
            modelUpdateListener.onIsTitleVisibleUpdated(newModel.isTitleVisible)
            modelUpdateListener.onIsTitleVisibleUpdated(currentModel?.isTitleVisible, newModel.isTitleVisible)
            modelUpdateListener.onIsTitleVisibleUpdated(newModel)
            modelUpdateListener.onIsTitleVisibleUpdated(currentModel, newModel)
        }

        currentModel = newModel
    }
}
```

- Implement the above interface, overriding whatever makes sense and pass all model updates
  through the Stateful wrapper class.
```kotlin
class View : ModelUpdateListener {
    private val statefulModel = StatefulModel(this)

    fun render(model: Model) {
        statefulModel.accept(model)
    }

    override fun onTitleUpdated(newTitle: String) {
        /* update the title */
    }
}
```

### Install
- Configure your project to consume GitHub packages
    - Generate an access token with `read packages` permission, more details here: [GitHub Help](https://help.github.com/en/packages/using-github-packages-with-your-projects-ecosystem/configuring-gradle-for-use-with-github-packages)
    - Add the maven repository to your `Project` dependencies
```groovy
allprojects {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/iFanie/Stateful")
            credentials {
                username = ...
                password = ...
            }
        }
    }
}
```

- Add kapt generated classes to your `Module` sourceSets
```groovy
sourceSets {
    main {
        java {
            srcDir "${buildDir.absolutePath}/generated/source/kaptKotlin/"
        }
    }
}
```

- Add the Stateful dependencies to your `Module`
```groovy
dependencies {
    implementation 'dev.fanie:stateful:0.0.1'
    kapt 'dev.fanie:stateful-compiler:0.0.1'
}
```

- You can use the `app` module as a reference for how the packages are accessed and used
