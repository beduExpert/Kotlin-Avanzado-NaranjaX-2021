[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 07`](../Readme.md) > `Proyecto`

## Proyecto

<div style="text-align: justify;">



### 1. Objetivos :dart:

* Implementar patrones de diseño y arquitectura a nuestro proyecto.
* Estudiar nuevos conceptos introducidos por jetpack.

### 2. Requisitos :clipboard:



### 3. Desarrollo :computer:

Implementar un patrón de arquitectura es sumamente importante en un proyecto, debido a que aporta escalabilidad, flexibilidad y modularidad. 

Implementaremos MVVM con Architecture components en nuestro proyecto para lograr estos objetivos. Quizá exista una cierta complejidad en readaptar el código de nuestro proyecto ya que generalmente, el diseño de la arquitectura es algo que se implementa y se crea desde el comienzo de la creación.



Esta arquitectura propuesta por Jetpack, Está conformada por los siguientes componentes:

* ___ViewModel___: Gestiona, recupera y provee de datos atados a un componente que conste de un ciclo de vida (Activity, Fragment, etc.). Como gestor de estado, depende únicamente de los repositorios con los que se comunica.
* ___LiveData___: Wrapper que permite volver una variable observable, respetando los ciclos de vida del componente que lo consuma.
* ___LifeCycleOwner___: Es una interfaz implementada en un ___AppCompactActivity___ o ___Fragment___ y nos permite suscribir componentes a su ciclo de vida, volviéndolos ***lifecycle-aware***. 



#### Modelo general

Para nuestro proyecto, implementaremos Nuestro patrón de arquitectura bajo el siguiente esquema:

<img src="images/architecture.png" width="70%">

Esto quiere decir que nuestros fragments almacenarán sus datos a través de un ViewModel con información observable a través de ___LiveData___. Estos datos se administrarán mediante un repositorio, encargado de recuperar datos del servidor mediante nuestro cliente http y gestionar la base de datos.



#### Repositorio y Retrofit



Para el uso de retrofit, podemos ahora hacer uso de coroutines en vez de utilizar callbacks. La interfaz tradicional de un método en retrofit tiene la siguiente forma:

```kotlin
interface ApiService{

	@GET("users")    
	fun getUsers(): Call<List<User>>

}
```

La clase Call contiene un método *enqueue* que nos permite agregar una serie de callbacks:

```kotlin
loginService.getUsers().enqueue(object: Callback<User> {

    override fun onResponse(call: Call<User>, response: Response<User>) {
        if(response.isSuccessful){
            // TODO: Gestionar respuesta
        } else{
            // TODO: Gestionar error
        }
    }

    override fun onFailure(call: Call<User>, t: Throwable) {
        // TODO: Gestionar error
    }
}
```

Esto se puede reducir drásticamente con coroutines, para esto se requiere utilizar una versión > retrofit 2.6. Nuestra interfaz quedaría así:

interface ApiService{    

@GET("users")   {

​	suspend fun getUsers(): List<User> 

}

```kotlin
class Repository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()
}
```



La implementación en un repositorio es el siguiente

```kotlin
class MainRepository(...,networking...) { 
suspend fun getUsers() = networking.loginService.getUsers()
}
```



y se puede manejar así desde el ViewModel:

```kotlin
fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
```





#### Two way DataBinding

En el ejemplo de esta sesión se abordó utilizar los valores del viewmodel en nuestro layout como parte de databinding, pero cómo podemos guardar actualizaciones desde un view en el layout hacia un viewmodel? existe una forma sencilla.

```xml
android:text="@={viewModel.user}"
```

En este ejemplo, nuestra variable es asignada mediante @={} en vez del @{}, para denotar esa acción.

#### Tips

* Maneja los datos del ___View___ directamente mediante data binding.
* Bajo ninguna circunstancia, manejes datos en tu ___Activity___ o ___Fragment___, gestionalos mediante métodos y asignaciones desde el ___ViewModel___.





### Lineamientos

1. Todos los layouts deben utilizar Data Binding, reemplazando este a ViewModel.

2. Se debe implementar el Architecture Components sugerido por android.

3. Cada  Fragment debe tener un ViewModel ya sea personal o compartido.

#### Recursos 

* [Cómo implementar retrofit con coroutines y un repositorio](https://medium.com/swlh/repository-pattern-in-android-c31d0268118c)

* [Two-way Data Binding](https://medium.com/@ssiasoft/two-way-data-binding-and-managing-text-inputs-in-android-9cc4701f628e)

[`Anterior`](../Reto-02) | [`Siguiente`](../Ejemplo-01)      

</div>

