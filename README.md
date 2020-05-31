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

Instead of rendering everything on every update or checking the updates manually

#### Annotate the Model.

```kotlin
@Stateful
data class Model( ... )
```

#### Build.

For any given annotated type, the processor will generate two classes
- A listener interface with functions that will br invoked when each individual public property
  is updated. For each property there will be 4 granular overloads of the callback, each with a
  default implementation to avoid clutter.

```kotlin
interface StatefulModelListener {
    fun onTitleUpdated(newTitle: String) {}
    fun onTitleUpdated(oldTitle: String?, newTitle: String) {}
    fun onTitleUpdated(newModel: Model) {}
    fun onTitleUpdated(oldModel: Model?, newModel: Model) {}

    fun onTitleFontSizeUpdated(newTitleFontSize: Float?) {}
    ...
}
```

- A wrapper class through which the new model instances will be passed and which, after diffing
  through the public properties, will invoke the listener appropriately.

```kotlin
class StatefulModel {
    fun accept(newModel: Model) { ... }
    fun clear() { ... }
}
```

#### Implement the generated interface.
Override whatever makes sense and pass all model updates through the Stateful wrapper class.

```kotlin
class View : StatefulModelUpdateListener {
    private val statefulModel = StatefulModel(this)

    fun render(model: Model) {
        statefulModel.accept(model)
    }

    override fun onTitleUpdated(newTitle: String) {
        /* update the title */
    }
}
```

### Stateful types
The `Stateful` annotation has a `type` argument, with a default value of `StatefulType.INSTANCE`.

- The `INSTANCE` type caches only the current value and performs diffing of new instances with
  the respective current.
- The `STACK` type holds a stack cache and allows performing a `rollback` to previous instances.
  What that means is that upon performing a rollback, the previous instance before the current one
  is accessed, diffing is performed with the current and the previous instance and the previous
  instance becomes the current.
- The `LINKED_LIST` type holds a linked list cache and allows rolling both back and forth. If a
  new instance is accepted while being in any state other than the latest, then the newly accepted
  istance is not announced and will only be announced when reaching it while going forth.

### Installation
#### Configure your project to consume GitHub packages.
- Generate an access token with `read packages` permission, more details here: [GitHub Help](https://help.github.com/en/packages/using-github-packages-with-your-projects-ecosystem/configuring-gradle-for-use-with-github-packages)
- Add the maven repository to your `Project` dependencies; `username` is your user ID and `password` is the key
  generated previously

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

#### Add the Stateful dependencies to your `Module`.

```groovy
dependencies {
    implementation 'dev.fanie:stateful:0.2.0'
    kapt 'dev.fanie:stateful-compiler:0.2.0'
}
```

##### You can use the `app` module as a reference for how the packages are accessed and used.

### Licence
```
Copyright 2020 Fanis Veizis

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
