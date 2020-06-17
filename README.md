# Stateful
Annotation processor library that generates a stateful wrapper and an Update listener interface for
model classes.

### Example
You have a simple Model and a View that renders the model.

```kotlin
data class Model(
    val title: String,
    val subtitle: String,
    val items: List<Item>?
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

The processor will generate a wrapper class for your Model, through which the new model instances 
will be passed and which, after diffing through the public properties, will invoke the listener 
appropriately.

```kotlin
class StatefulModel {
    fun accept(newModel: Model) { ... }
    fun clear() { ... }

    sealed class Property {
        object TITLE: Property
        object SUBTITLE: Property
        object ITEMS: Property
    }
}
```

#### Create your property renderers.
Create functions that render the properties and decorate them with the `@Renders` annotation,
passing the appropriate `Property` implementation class.

```kotlin
class View {
    private val statefulModel by stateful()

    fun render(model: Model) {
        statefulModel.accept(model)
    }

    @Renders(StatefulModel.Property.TITLE::class)
    fun renderTitle(newTitle: String) { /* update the title view */ }

    @Renders(StatefulModel.Property.SUBTITLE::class)
    fun renderSubtitle(newSubtitle: String) { /* update the subtitle view */ }

    @Renders(StatefulModel.Property.ITEMS::class)
    fun renderItems(items: List<Item>?) { /* update the items view */ }
}
```

##### Renderer arguments.
You can create your rendering functions using any (even more than one per property) of the following
signature configurations:

* New value:
```kotlin
@Renders(StatefulModel.Property.TITLE::class)
fun renderTitle(newTitle: String) { /* update the title view */ }
```

* Current value (must be optional) and new value:
```kotlin
@Renders(StatefulModel.Property.TITLE::class)
fun renderTitle(currentTitle: String?, newTitle: String) { /* update the title view */ }
```

* New model:
```kotlin
@Renders(StatefulModel.Property.TITLE::class)
fun renderTitle(newModel: Model) { /* update the title view */ }
```

* Current model (must be optional) and new model:
```kotlin
@Renders(StatefulModel.Property.TITLE::class)
fun renderTitle(currentModel: Model?, newModel: Model) { /* update the title view */ }
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
  instance is not announced and will only be announced when reaching it while going forth.

### Stateful options
The `Stateful` annotation has an `options` array argument, with an empty default value.

- When the `NO_LAZY_INIT` option is applied, the top level functions for lazy delegation of the 
  generated wrapper initialization will not be generated. Use this if you intend to use the
  constructors directly and don't need the overhead.
- When the `NO_DIFFING` option is applied, no diffing will be performed on the properties of the 
  annotated model and the listener will be invoked on every new instance received. Use this if you
  do not need any diffing but would like the rest of the provided setup.
- When the `ALLOW_MISSING_RENDERERS` option is applied, the listener is allowed to omit a subset of 
  properties when creating `@Renders` annotated functions which will cause a 
  `RendererConfigurationException` with a `RendererConfigurationError.NO_MATCHING_RENDERERS_FOUND`
  error to be to be thrown otherwise. Use this if you don't care to render all the properties of
  the Model in the current View.
- When the `WITH_LISTENER` option is applied, separate interfaces, one for each public property of 
  the annotated model, will be generated, all of which are extended by a master interface. Implementing
  the master interface allows you to have the functionality without needing to annotate anything. 
  Use this if you don't want to have extra annotations in your View or don't like/want reflection in
  your code.
- When the `WITH_NON_CASCADING_LISTENER` option is applied, only a single listener interface will be 
  generated containing callbacks for every single public property in the annotated model. Use this
  if you want to have the Listener setup don't want multiple interfaces added to the class count.


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
    implementation 'dev.fanie:stateful:0.4.0'
    kapt 'dev.fanie:stateful-compiler:0.4.0'
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
