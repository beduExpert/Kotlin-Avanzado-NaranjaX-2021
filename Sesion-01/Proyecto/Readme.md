[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 01`](../Readme.md) > `Proyecto`

## Proyecto

<div style="text-align: justify;">

### 1. Objetivos :dart:

- Agregar transiciones al proyecto
- Animar elementos al mostrarlos u ocultarlos

### 2. Requisitos :clipboard:

1. Android Studio Instalado en nuestra computadora.


### 3. Desarrollo :computer:



¡Bienvenido de nuevo!

En esta parte del proyecto, retomaremos el proyecto realizado en Kotlin Intermedio e implementaremos algunas animaciones y transiciones a la aplicacióna la aplicación.



### Animaciones entre el flujo de invitado

El primer punto que abordaremos son las animaciones que ejecutaremos entre la pantalla de login y la de registro. Para esto, dividiremos las animaciones para cada acción:

* Al navegar de la pantalla de login a la de registro, el login debe salir hacia el lado izquierdo y la de registro entrar desde la derecha. 
* Al navegar hacia atrás desde la pantalla de registro, la pantalla de login debe de entrar desde la izquierda y la de registro salir hacia la derecha.
* Al pulsar el botón de registrarse, la pantalla de login debe entrar desde la derecha y la de registro desplazarse a la izquierda.

El resultado final se visualiza en el siguiente gif:

<img src="./images/login-transition-flow.gif" width="35%">



### Transición entre lista de productos y detalle

Ahora, implementaremos una transición de elementos compartidos entre un elemento de la lista de productos y el detalle de este. 

Al pulsar sobre un elemento de la lista, el elemento debe resaltarse y expandirse para finalmente tomar el tamaño del contenedor final. La animación al dar navegar hacia atrás, debe ser la inversa a la que describimos.


Mostramos a continuación el resultado de este ejercicio (la velocidad de la transición está reducida para poder apreciar los detalles de la animación).

<img src="./images/transition-list.gif" width="40%">



### Mostrar/ocultar Bottom Navigation



En este último bloque, mostraremos y ocultaremos nuestro ___BottomNavigationView___ en función del fragment que esté en primer plano. A continuación enumeramos las pantallas donde se debe mostrar nuestro view.

1.  En la lista de productos.
2. En el carrito de compras.
3. En el perfil de usuario.

El Animator debe mostrar la barra surgiendo desde abajo hacia arriba y al llegar a su posición, poder realizar operaciones sobre él. Al ocultarse, se deshabilitan las acciones que se pueden ejecutar y la barra desde su punto se desplaza hacia abajo hasta desaparecer.



El sigiuiente bloque describe una estructura en donde se pueden controlar estos eventos. En este caso, el navController se podría recuperar desde el mismo ___BottomNavigationView___, y mediante un listener, determinar si la barra se oculta o se muestra en función del fragmento al que se navega (identificado por medio de sus id's).  

```kotlin
lifecycleScope.launchWhenResumed {
    TODO("Aquí va el elemento de donde se obtiene el navController").findNavController().addOnDestinationChangedListener { _, destination, _ ->
        Log.d("destination", destination.toString())
        when (destination.id) {
            TODO("Aquí van los id's de los elementos donde se muestra el view") -> {
              // TODO: Escribe aquí la lógica para mostrar el View  
            }
            else -> {
              // TODO: Escribe aquí la lógica para ocultar el View
            }
        }
    }
}
```

La ejecución de esta tarea no se limita a este bloque de código, así que otra forma de controlar estos eventos es permitida.

[`Anterior`](../Reto-02) | [`Siguiente`](../Readme.md)

</div>
