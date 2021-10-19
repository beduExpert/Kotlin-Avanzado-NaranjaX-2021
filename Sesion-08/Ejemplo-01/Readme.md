[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 08`](../Readme.md) > `Ejemplo 1`

## Ejemplo 1: Pruebas unitarias

<div style="text-align: justify;">




### 1. Objetivos :dart:

* Realizar pruebas de clases y métodos aislados  en específico en la JVM.

### 2. Requisitos :clipboard:

* JUnit y Thruth instalados.

### 3. Desarrollo :computer:

1. Creamos un nuevo proyecto con empty Activity y agregamos las dependencias de pruebas unitarias

```kotlin
    testImplementation 'junit:junit:4.13'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'

    testImplementation "com.google.truth:truth:1.0.1"
    androidTestImplementation "com.google.truth:truth:1.0.1"
```

es muy importante notar que las bibliotecas que estamos agregando vienen de dos fuentes distintas.

2. Vamos a definir pruebas unitarias sobre una función de registro de usuarios. Para esto vamos a seguir una metodología en la que primero se definen las pruebas unitarias y posteriormente se da la especificación de la función. 

3. Creamos un nuevo archivo de Kotlin de tipo **Object** con el nombre *RegistrationUtil* a la misma altura que se encuentra el Main activity. Dentro de este nuevo archivo agregamos la firma de la función sobre la cual vamos a definir las pruebas unitarias.

```kotlin
fun validateRegistrationInput(
        username: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        return true
    }

```

Esta función por ahora simplemente regresa True sin importar cuales sean sus argumentos.

4. Vamos a agregar una especificación informal del funcionamiento correcto de nuestra aplicación en forma de comentario. Esto no representa ningún avance en la verificación del código simplemente es una guía para nosotros, que nos ayudará a saber cuales son los aspectos a testear sobre esta función.

```kotlin
    /**
     * La entrada no es válida si ...
     * ... username/password es vacío
     * ...ya se había registrado el username
     * ...Las contraseña no coinciden
     * ...La contraseña tiene una longitud menor a 8 caracteres
     */

```

Para verificar el segundo punto definimos una lista de usernames ya utilizados.

```language
private val existingUsers = listOf("Peter", "Carl")
```
5. Ahora generaremos un archivo de testing para esta clase. Para eso damos click derecho sobre el nombre del Objeto y seleccionamos la opción de Generate > Test...

Vamos a usar los siguientes valores para el archivo de testing:

- Testing Library : JUnit4
- Class name: RegistrationUtilTest

el resto de los valores los dejamos en blanco. Damos click en Ok. 

6. Nos pide que seleccionemos el directorio en el cual se quiere agregar la nueva clase de pruebas unitarias. Para este caso seleccionaremos simplemente test. Ya que no se relaciona con los componentes de android. Al dar click en Ok se genero el nuevo archivo con la clase RegistrationUtilTest, dentro de esta clase ya podemos definir las pruebas unitarias.

7. Definamos la prueba unitaria para el caso en el que el usuario o contraseña son vacíos. Como sigue:

```kotlin
    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }
```

La anatomía de una pruieba unitaria es la siguiente:

- La anotación `@Test`.
- Se define como una función sin parámetros ni tipo de regreso.
- En el cuerpo de la función se hace la llamada a la funcionalidad que estamos testeando, con ciertos parámetros.
- Se hace una declaración 

Trhuth se va a encargar de verificar esta declaración sea cierta, en este caso la prueba paso, en el caso contrario la prueba falla.

La declaración va a depender del tipo de dato que arroje como resultado la funcionalidad testeada. Por ejemplo

```kotlin
assertThat("hola")
```
tiene opciones distintas.

Para que assert funcione correctamente tenemos que importarla desde Truth no de google como lo hace automáticamente AndroidStudio al crear la clase, por lo que borramos el import y la importamos directo de Truth.

Al ejecutar esta prueba unitaria, vemos que no pasa pues estamos esperando un valor de regreso False y nuestra función siempre regresa True. 

8. Definimos ahora una función que si pase la prueba.

```kotlin
    @Test
    fun `valid username and correctly repeated password returns true`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Philipp",
            "123",
            "123"
        )
        assertThat(result).isTrue()
    }
```

En este caso todos los campos estan presentes por lo que la prueba unitaria debería pasar.

9. Damos una prueba unitaria para el caso en el que el username ya este registrado que falle.

```kotlin
    @Test
    fun `username already exists returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Carl",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }
```

10. **Ejercicio** definamos ahora uno que pase el criterio anterior.

11. Escribimos la prueba que falla cuando las contraseñas no coinciden.

```kotlin
    @Test
    fun `incorrectly confirmed password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Philipp",
            "123456",
            "abcdefg"
        )
        assertThat(result).isFalse()
    }
```

11. **Ejercicio** definamos ahora una prueba que pase el criterio de coincidencia de contraseñas.

12.  La siguiente prueba pasa en el último criterio respecto a la longitud de la contraseña.

```kotlin
    @Test
    fun `less than 2 digit password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Philipp",
            "abcdefg5",
            "abcdefg5"
        )
        assertThat(result).isFalse()

        assertT
    }
```
13. **Ejercicio** definamos ahora una prueba que no pase el criterio de longitud.


14. Recordemos que estamos trabajando con un desarrollo a la inversa en donde a partir de las pruebas unitarias se da la implementación para la función que estamos testeando. Entonces regresamos a `RegistrationUtil` e implementamos la función.

```kotlin
    fun validateRegistrationInput(
        username: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        if(username.isEmpty() || password.isEmpty()) {
            return false
        }
        if(username in existingUsers) {
            return false
        }
        if(password != confirmedPassword) {
            return false
        }
        if(password.count { it.isDigit() } < 2) {
            return false
        }
        return true
    }
```


[`Anterior`](../) | [`Siguiente`](../Reto-01)      

</div>

