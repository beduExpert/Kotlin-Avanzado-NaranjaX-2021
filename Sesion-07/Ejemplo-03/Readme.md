[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 07`](../Readme.md) > `Ejemplo 3`

## Ejemplo 3: RxKotlin

<div style="text-align: justify;">




### 1. Objetivos :dart:

- Entender el patrón Observable y su implementación para ReactiveX
- Comprender el uso de RxKotlin (Wrapper de RxJava)

### 2. Requisitos :clipboard:

* Haber estudiado previamente los temas en el prework relacionados a este.

### 3. Desarrollo :computer:

RXKotlin es una implementación open-source de la librería ReactiveX que ayuda a crear aplicaciones en el estilo de programación reactivo. Aunque RXKotlin está diseñado para procesar flujos sincrónicas y asincrónicas de datos, no se restringe a tipos de datos "tradicional". Definición de RXKotlin de "datos" es bastante amplio. Simplemente porque tu aplicación no trata con números grandes o realiza transformaciones de datos complejos, no significa que no puedas beneficiarte de RXKotlin!

#### ¿Cómo Funciona RxKotlin?

RxKotlin extiende el patrón de diseño Observer software, que se basa en el concepto de observadores y Observables. Para crear una tubería de datos básica de RxKotlin, es necesario:

- Crear un Observable.
- Darle al Observable datos a emitir.
- Crear un observador.
- Suscribirse al observador el Observable.

La observabilidad es la capacidad de un objeto para notificar a otros sobre cambios en sus datos. La biblioteca de vinculación de datos te permite hacer que objetos, campos o colecciones sean observables.

Tan pronto como el Observable tiene al menos un observador, comenzará a emitir datos. Cada vez el Observable emite un dato, lo pasa a su observador llamando al método `onNext()`, y el observador entonces típicamente realizará alguna acción en respuesta a esta emisión de datos. Una vez que el Observable ha terminado de emitir datos, le notificar al observador llamando al método  `onComplete()`. El Observable entonces se terminará, y pondrá fin a la secuencia de datos.

Si se produce una excepción, entonces se llamará `onError()` y el Observable terminará inmediatamente sin emitir más datos o llamando al `onComplete()`.

Vamos a implementarlo en código.

1. Instalamos las dependencias

```kotlin
implementation "io.reactivex.rxjava2:rxkotlin:2.4.0"
implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
```

2. Veamos primero como funciona un Observable, para eso generamos una lista con números del 1 al 8 e imprimimos em pantalla sus potencias cuadradas: 

```kotlin  
val numsObservable = listOf(1,2,3,4,5,6,7,8) //lista del uno al ocho
            .toObservable() //Volveéndolo observable
            .observeOn(AndroidSchedulers.mainThread()) //correr en el main thread
            .map {number -> number*number} //número al cuadrado en la lista
            .subscribeBy ( //Gestionando los tres callbacks : 
                onError =  { it.printStackTrace() }, //cuando alguno de la iteración falla
                onNext = { println("numero: $it") }, //cuando se reproducjo una nueva iteración
                onComplete = {  } //cuando se completó la o
            )
```

Corremos la app y consultamos el logcat, visualizaremos lo siguiente:

<img src= "01.png" width="100%"/>


3. Ahora hagamos algo un poco mas interesante, guardaremos una lista de nombres por medio de un observable, para eso necesitamos un Layout con un *ListView* llamado ***lista***.

```xml

    <ListView
        android:id="@+id/lista"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

```

```kotlin
private lateinit var lista : ListView
...
lista = findViewById(R.id.lista)
```


4. Inicializamos una lsita de nombres y creamos un ArrayAdapter para simplificar la construcción de la lista. 

```kotlin
var names = arrayListOf("Juan")
val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,names)
lista.adapter = adapter
```

5. Y seteamos nuestro observable, que hara que se agregue el resto de nombres de la lista

```kotlin
        val observable = listOf("Manuel", "Agnès", "Frida", "Anaïs")
            .toObservable()
            .observeOn(AndroidSchedulers.mainThread()) //correr en el main thread
            .subscribeBy (
                onError =  { it.printStackTrace() },
                onNext = {
                    names.add(it)
                    adapter.notifyDataSetChanged()
                },
                onComplete = { }
            )
```

Corre el proyecto, en la pantalla debe aparecer esta pantalla: 

<img src="02.png" width="33%"/>

[`Anterior`](../Reto-01/Readme.md) | [`Siguiente`](../Readme.md)      

</div>





