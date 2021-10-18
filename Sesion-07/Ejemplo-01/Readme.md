[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 07`](../Readme.md) > `Ejemplo 1`

## Dependency Injection

<div style="text-align: justify;">

### 1. Objetivos :dart:


- Entender el uso de la arquitecruta DI

### 2. Requisitos :clipboard:

* Haber leído previamente el tema de Patrones de arquitectura para android en el Prework

### 3. Desarrollo :computer:

La inyección de dependencias (DI) es una técnica muy utilizada en programación y adecuada para el desarrollo de Android. Si sigues los principios de la DI, sentarás las bases para una buena arquitectura de apps.

Implementar la inyección de dependencias te proporciona las siguientes ventajas:

 * Reutilización de código
 * Facilidad de refactorización
 * Facilidad de prueba


#### Que es una dependencía

Una dependencia ocurre cuando un objeto de una clase requiere un objeto de otra clase para funcionar correctamente. Estas dependencias suelen ser variables miembro de la clase. Para que tenga una mejor idea de cómo podría funcionar una dependencia, considere el siguiente ejemplo: una sala de cine requiere al menos una pantalla, un proyector y una película; de lo contrario, no funcionará.

Veamos esto como se ve en el código

1. Crea un archivo `MovieTheatre` con el siguiente código:

```kotlin
class MovieTheatre {  

  var screen: Screen  
  var projector: Projector  
  var movie: Movie  

  init {  
    screen = Screen()  
    projector = Projector()  
    movie = Movie()  
  }  

  fun playMovie() {  
    System.out.println("Playing movie now")  
  }  
}
```

En esta clase se puede observar una dependencia hacia las clases `Screen`, `Projector` y `Movie`. al ser necesarias para crear una instancia de la clase. Creamos estas clases vacías.

Si bien la creación de instancias de dependencias en `init` puede parecer correcta a primera vista, existen algunos problemas con este enfoque.

Cuando crea una instancia de las dependencias de una clase en su `init`, se crea una situación en la que la clase está estrechamente acoplada con sus dependencias. En este caso, MovieTheatre está estrechamente vinculado con sus dependencias (pantalla, proyector y película), por lo que cada vez que crea una nueva instancia de MovieTheatre, se crea una pantalla, un proyector y una película.

El problema es que la clase tiene la responsabilidad de crear y usar estos objetos. Como regla general, al crear una clase, se debe separar la creación de un objeto del uso de este.

Considera el siguiente escenario: hay dos salas de cine, una con un proyector que usa una película de 8 mm y otra con un proyector que usa una película de 16 mm. Si tuviera que crear dos instancias de MovieTheatre, cada una con su propio proyector, no tendría forma de especificar el tipo de película porque el `init` actual no permite ese tipo de flexibilidad.

Para garantizar la reutilización de la clase `MovieTheatre`, es mejor crear dos instancias de `Projector` en otro lugar y pasarlas a su respectiva instancia `MovieTheatre`. En cualquier caso, `MovieTheatre` toma cualquier proyector que tenga y luego hace lo que debe hacer para mostrar la película, independientemente del tipo de película.

Otra razón para evitar instanciar dependencias en el bloque `init` de un objeto es la prueba unitaria. Al realizar pruebas unitarias de `MovieTheatre`, solo te interesa probar el comportamiento de esa clase, no ninguna de sus dependencias. Por ejemplo, un proyector puede tener un funcionamiento interno complicado involucrado en la visualización de una película. Suponiendo que ya realizó pruebas unitarias en `Projector`, no es necesario que las pruebe como parte de las pruebas unitarias de `MovieTheatre`. Sin embargo, si crea una instancia de `Projector` en el bloque `init` de `MovieTheatre`, estará probando efectivamente el comportamiento de dependencia junto con el comportamiento del objeto de interés.

2. Modificamos entonces la clase `MovieTheatre` para que use inyección de dependencias, de la siguiente forma:

```kotlin
class MovieTheatre(val screen: Screen, val projector: Projector, val movie: Movie) {  

  fun playMovie() {  
    System.out.println("Playing movie now")  
  }  
}
```

3. Instanciamos un objeto de la clase en `MainActivity`

```kotlin
class MainActivity : AppCompatActivity() {  

  override fun onCreate(savedInstanceState: Bundle?) {  
    super.onCreate(savedInstanceState)  
    setContentView(R.layout.activity_main)  

    //Se crean las dependencias
    val screen = Screen()  
    val projector = Projector()  
    val movie = Movie()  

    //Se crea el objeto inyectando las dependencias
    val movieTheatre = MovieTheatre(screen, projector, movie)

    //llamamos al método de la clase para ver que funcione
    movieTheatre.playMovie()  
  }  
}

```

Al correr el proyecto podemos ver en los logs que realmente funciona. Sin embargo nos enfrentamos a otro problema.

Inyectar dependencias a través del constructor está bien para aplicaciones simples como esta aplicación. Sin embargo, una aplicación más complicada puede tener una red de dependencias.

Supongamos que la pantalla tiene sus propias dependencias como una cortina y un fondo, mientras que el proyector tiene sus propias dependencias como una lente, un cable de alimentación y un carrete. Cada una de estas dependencias debe instanciarse y pasarse a la clase respectiva (Pantalla o Proyector) al crear esa clase, y luego `Screen`, `Projector` y `Movie` deben pasarse para crear un objeto `MovieTheatre`. El resultado es una gran cantidad de código repetitivo.

Si se necesitan demasiadas líneas de código antes de poder crear una instancia de su clase principal, es una señal de que un framework de arquitectura de inyección de dependencia puede ayudarnos.

4. ahora usaremos el framework `Dagger` que nos ayuda a simplificar la inyección de dependencias en la aplicación a partir de anotaciones en nuestro código. En general se usan estas dos anotaciones:

- `@Inject`: esta anotación marca qué dependencias inyectar. Puede usarlo en un constructor, campo o método.
- `@Component`: esta anotación se usa en una interfaz desde la cual Dagger generará una nueva clase que contiene métodos que devuelven objetos con sus dependencias inyectadas, de forma automática.

Primero tenemos que agregar las dependencias al archivo `app/build.grade`

```kotlin
id 'kotlin-kapt'

...

//Dagger dependencies  
implementation 'com.google.dagger:dagger:2.15'  
kapt 'com.google.dagger:dagger-compiler:2.15'  
kapt 'com.google.dagger:dagger-android-processor:2.15'

```

5. Ahora vamos a usar la anotación `@Inject` en cada una de las clases que definen las dependencias de la clase `MovieTheatre`.

- `Projector`

```kotlin
class Projector @Inject constructor()

```

- `Screen`

```kotlin
class Screen @Inject constructor()

```
- `Movie`

```kotlin
class Movie @Inject constructor()

```

Y por último agregamos también la anotación en la clase `MovieTheatre` para indicar que el contrsuctor va a utilizar inyección de dependencias a través de Dagger.

```kotlin
class MovieTheatre @Inject constructor(val screen: Screen, val projector: Projector, val movie: Movie) {
  ...
}
```

6. Creamos una interfaz que sea la encargada de crear instancias de `MovieTheatre`, para eso usamos la anotación `@Component`.

```kotlin
@Component  
interface MovieTheatreComponent {  
  fun getMovieTheatre() : MovieTheatre  
}
```

Es importante ver que el método `getMovieTheatre()` no se encuentra implementado y es Dagger el que se encarga de implementarlo. Para esto hacemos un rebuild del proyecto.

7. Tras el rebuild Dagger genero una clase `DaggerMovieTheatreComponent` que implementa la interfaz que definimos por lo que tiene el m'etodo `getMovieTheatre` con el que ya podemos instanciar la clase utilizando una inyección de dependencias implícita gracias a Dagger. Por lo que en `MainActivity` cambiamos el código por:

```kotlin
val movieTheatre = DaggerMovieTheatreComponent.create().getMovieTheatre()

movieTheatre.playMovie()

```

Y el resultado es el mismo.

[`Anterior`](../Readme.md) | [`Siguiente`](../Proyecto/Readme.md)      

</div>