[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 08`](../Readme.md) > `Reto 1`

## Reto 1: Pruebas unitarias

<div style="text-align: justify;">

### 1. Objetivos :dart:

* Realizar pruebas unitarias

### 2. Requisitos :clipboard:



### 3. Desarrollo :computer:

Para este reto tendras que escribir pruebas unitarias que testeen la correctud de las siguientes funciones.

1. Define pruebas unitarias para la función que fibonacci definida como sigue:

```kotlin

    fun fib (n:Int): Long {
        if (n == 0 || n == 1){
            return n.toLong()
        }

        var a = 0L
        var b = 1L
        var c = 1L

        (1..n-2).forEach{ i ->
            c = a + b
            a = b
            b = c
        }
        return c
    }
```

2. Define pruebas unitarias para la función que verifica que los paréntesis de una expresión estén balanceados, es decir que haya la misma cantidad de paréntesis que abren y paréntesis que cierran.

```kotlin
    fun checkBraces(s:String) : Boolean {
        return s.count{ it == '(' } == s.count { it == ')'}
    }

```

Para ambos casos define cuál es el correcto funcionamiento de las funciones y haz 3 pruebas que definan casos correctos y 3 que definan casos incorrectos. Con el resultado de tus pruebas unitarias di si las funcioens son correctas o no, en caso de no serlo ¿Que cambios son necesarios para que las funciones sean correctas?




[`Anterior`](../Ejemplo-01) | [`Siguiente`](../Ejemplo-02)      

</div>

