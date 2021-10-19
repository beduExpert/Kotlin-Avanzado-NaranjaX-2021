

[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 08`](../Readme.md) > `Ejemplo 2`

## Ejemplo 2: Integrated Test

<div style="text-align: justify;">

### 1. Objetivos :dart:


* Realizar pruebas entre clases de Android


### 2. Desarrollo :computer:

Para este ejemplo crearemos pruebas unitarias que van a trabajar sobre los componentes de Android, a estas pruebas se les llama Pruebas integradas.

1. Sobre el mismo proyecto del ejemplo anterior creamos una clase en la que definimos una función que trabaja con un contexto y un recurso para saber si son iguales a una cadena de texto. La clase tendrá el nombre `ResourceComparer` y tendrá el siguiente contenido

```kotlin
class ResourceComparer {

    fun isEqual (context: Context, resId: Int, string: String): Boolean {
        return context.getString(resId) == string
    }
}

```

2. Creamos un archivo de pruebas para esta clase dando click derecho en el nombre de la clase Generate > Test ...
Vamos a usar los siguientes valores para el archivo de testing:

- Testing Library : JUnit4
- Class name: RegistrationUtilTest

el resto de los valores los dejamos en blanco. Damos click en Ok. 

3. En este caso como la función está trabajando con un contexto y los recursos de la aplicación se trata de una prueba sobre componentes de Android. Por lo que lo agregamos a la carpeta correspondiente. 

4. Notemos como en este ejemplo es necesario crear una instancia de la clase `ResourceComparer` para poder definir pruebas sobre ellos, esto debido a que nuestra clase no se trata de un singleton (Object). para esto veremos 3 enfoques distintos para solucionarlo. El primero es declararlo como variable global en la clase de pruebas unitarias

```kotlin
val resourceComparer = ResourceComparer()
```

y posteriormente lo utilizamos en nuestras pruebas.

5. Definimos una prueba que debe regresar verdadero en el uso de la función `isEqual` 

```kotlin
    @Test
    fun stringResourceSameAsGivenString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "UnitTesting")
        assertThat(result).isTrue()
    }
```

6. Ahora definimos una prueba que sea falso.

```kotlin
    @Test
    fun stringResourceDifferentAsGivenString_returnsFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "Hello")
        assertThat(result).isFalse()
    }
```

Al ejecutarlas podemos ver que funcionan correctamente y que las pruebas pasan por lo que la definición de la función es correcta bajo esas entradas. Sin embargo tenemos un problema, un requisito sobre las pruebas unitarias es que sean independientes entre sí, esto es, que la ejecución de una no afecte a la otra. Y al usar la misma instancia de `ResourceComparer` no se cumple esta propiedad. 

7. Para corregir el problema de la independencia entre pruebas podemos instanciar cada `ResourceComparer` dentro de la definición de la prueba. Para ello primero definimos la variable como un `lateinit`

```kotlin
private lateinit var resourceComparer : ResourceComparer
```

Y lo instanciamos dentro de cada prueba. 

```kotlin
resourceComparer = ResourceComparer()
```

8. Volvemos a correr la prueba y podemos ver que funcionan correctamente y la definición de la función es correcta bajo estas pruebas. Sin embargo el código definido en las se vuelve repetitivo. 

9. Vamos a corregir la repetición del código usando una herramienta de JUnit que nos ayudará en este caso. 

10. Definimos una función `setup` que se encargue de instanciar `ResourceComparer` como sigue:

```kotlin
    fun setup() {
        resourceComparer = ResourceComparer()
    }
```
 
11. lo que queremos en este caso es que esta función se ejecute antes de cada prueba unitaria, para eso añadimos la anotación `@Before`. También podemos agregar la recuperación del contexto dentro de `setup` por lo que queda de la siguiente forma:

```kotlin
    @Before
    fun setup() {
        resourceComparer = ResourceComparer()
        context = ApplicationProvider.getApplicationContext<Context>()
    }
``` 

12. Y cada uno de las pruebas simplemente hacen la llamada a la función

```kotlin
    @Test
    fun stringResourceSameAsGivenString_returnsTrue() {
        val result = resourceComparer.isEqual(context, R.string.app_name, "UnitTesting")
        assertThat(result).isTrue()
    }
    @Test
    fun stringResourceDifferentAsGivenString_returnsFalse() {
        val result = resourceComparer.isEqual(context, R.string.app_name, "Hello")
        assertThat(result).isFalse()
    }
```

13. Así como existe la anotación `@Before` también tenemos una llamada `@After` que ejecuta el código después de cada una de las pruebas. En este bloque se destruyen todas los componentes creados, para nuestro ejemplo no tiene sentido hacerlo pues el único componente que creamos es `ResourceComparer` y ese es liberado directamente por el recolector de basura. Pero la sintaxis sería la siguiente:

```kotlin
    @After
    fun after () {
        // destruir todos los objetos creados
    }
```

14. Volvemos a correr las pruebas y notamos que siguen funcionando correctamente.

15. Es importante notar que a diferencia de las pruebas del ejemplo anterior, estas ejecutan el emulador. Pues están trabajando con los componentes de android que solo se intancian en la ejecución de la aplicación.


[`Anterior`](../Reto-01) | [`Siguiente`](../Readme.md)      

</div>