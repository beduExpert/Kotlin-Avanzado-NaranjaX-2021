[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 07`](../Readme.md) > `Ejemplo 2`

## Patrón de arquitectura: Model-View-Presenter :

<div style="text-align: justify;">




### 1. Objetivos :dart:

- Migrar una actividad convencional al patrón Model-View-Presenter

### 2. Requisitos :clipboard:

* Haber leído previamente el tema de Patrones de arquitectura para android en el Prework

### 3. Desarrollo :computer:


El patrón MVP (Model View Presenter) es un derivado del conocido MVC (Model View Controller), y uno de los patrones más populares para organizar la capa de presentación en las aplicaciones de Android.

#### ¿Qué es MVP?
El patrón MVP permite separar la capa de presentación de la lógica para que todo sobre cómo funciona la interfaz de usuario sea independiente de cómo lo representamos en la pantalla. Idealmente, el patrón MVP lograría que la misma lógica pudiera tener vistas completamente diferentes e intercambiables.

Lo primero que hay que aclarar es que MVP no es una arquitectura en sí misma, solo es responsable de la capa de presentación. Varias personas me han rebatido esto, por lo que quiero explicarlo un poco más a fondo.

Puedes ver en muchos sitios que MVP se define como un patrón de arquitectura porque puede convertirse en parte de la arquitectura de su aplicación, pero no consideres que solo porque estás usando MVP, tu arquitectura está completa. MVP solo modela la capa de presentación, pero el resto de capas aún requerirá una buena arquitectura si quieres una aplicación flexible y escalable.

#### Cómo implementar MVP para Android
Bueno, aquí es donde todo comienza a volverse más difuso. Hay muchas variaciones de MVP y todos pueden ajustar el patrón a sus necesidades y la forma en que se sienten más cómodos. Varía dependiendo básicamente del número de responsabilidades que delegamos al presentador.

¿Es la vista responsable de habilitar o deshabilitar una barra de progreso, o debe ser realizada por el presentador? ¿Y quién decide qué acciones deben mostrarse en la ActionBar? Ahí es donde comienzan las decisiones difíciles.

Vamos a aprender implementar lo esencial del patrón MVP en un proyecto previamente creado.

Para esto, utilizaremos el [Proyecto base](base) en esta carpeta e iremos implementando las modificaciones.

1. Vamos a utilizar a AddContactActivity para hacer nuestra migración, para eso creamos un nuevo package llamado *addcontact* en donde se encuentra nuestro *MainActivity*.


2. Ahí colocaremos nuestros archivos *AddContactActivity* (nuestro View) y la clase *Contact* (nuestro Model) y creamos una tercera clase llamado *AddContactPresenter*

<img src="img/01.png" width="25%"/>

#### El modelo
En una aplicación con una arquitectura por capas completa, este modelo solo sería la puerta de entrada a la capa de dominio o la lógica empresarial.

#### The View
La vista, generalmente implementada por una Activity (puede ser un Fragment, una View… dependiendo de cómo esté estructurada la aplicación), contendrá una referencia al presentador. El presentador será idealmente provisto por un inyector de dependencias como Dagger, pero en caso de que no use algo como esto, esta vista será la responsable de crear el objeto presentador. La vista llamará a un método de presentador cada vez que haya una acción del usuario.

3. Agregamos al *AddContactPresenter* el siguiente código.

### El Presenter

El Presenter es responsable de actuar como intermediario entre la vista y el modelo. Recupera datos del modelo y los devuelve formateados a la vista.

Además, a diferencia del MVC típico, decide qué sucede cuando se interactúa con la vista. Por lo tanto, tendrá un método para cada posible acción que el usuario pueda hacer. Lo vimos en la vista, pero aquí está la implementación:

```kotlin
package org.bedu.recyclercontacts.addcontact

import android.app.Activity
import android.content.Intent


class AddContactPresenter( view: View) { //view es la vista a la que estará atado (AddContactPresenter)

    //el Model al que estamos atados
    var contact=Contact()

    //Actualizamos nuestro Model desde el presenter cada que se actualiza el nombre
    fun updateName(name: String){
        contact.name = name
    }

    //Actualizamos nuestro Model desde el presenter cada que se actualiza el teléfono
    fun updatePhone(phone: String){
        contact.phone = phone
    }

    //Acción a tomar cuando se presiona el botón addContact
    fun addContact(activity:Activity){

        val returnIntent = Intent()
        returnIntent.putExtra("new_contact", contact)
        activity.setResult(Activity.RESULT_OK, returnIntent)
        activity.finish()
    }

    //interfaz que define nuestra vista
    interface View{
        fun addContact()
    }
}
```

Como el presentador debe ser independiente de la vista, utiliza una interfaz que debe ser implementada.

4. Cambiaremos la lógica de AddContactActivity para que se use el presenter:

```kotlin
class AddContactActivity : AppCompatActivity(),AddContactPresenter.View {

    //nueva instancia de nuestro presentador
    private val presenter = AddContactPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        //llamamos la función addContact
        buttonAdd.setOnClickListener{
            addContact()
        }

        //Cuando el texto cambia (onTextChanged), el presenter hace una actualización de nuestro nombre
        editName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.updateName(s.toString())
            }
        })

        //Cuando el texto cambia (onTextChanged), el presenter hace una actualización de nuestro teléfono
        editPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.updatePhone(s.toString())
            }
        })
    }

    //implementación de la interfaz definida en presenter, en este caso sólo llama a la función del presenter
    //pero aquí podría actualizarse algún estado de un elemento de la Vista
    override fun addContact() {
        presenter.addContact(this)
    }

}
```

5. Corremos la app, el funcionamiento no debió ser alterado.

MVP tiene algunos riesgos, y lo más importante de lo que nos solemos olvidar es que el presentador está conectado a la vista para siempre. Y la vista es una actividad, lo que significa que:

- Podemos producir un leak de la actividad si ejecutamos tareas largas en segundo plano
- Podemos intentar actualizar una activity que ya haya muerto

[`Anterior`](../Proyecto) | [`Siguiente`](../Ejemplo-03)      

</div>