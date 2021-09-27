[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 08`](../Readme.md) > `Proyecto`

## Proyecto

<div style="text-align: justify;">

### 1. Objetivos :dart:

* Crear test unitarios a nuestras clases.
* Hacer tests de acciones específicas en la UI de nuestra app.

### 2. Requisitos :clipboard:

* Instalar las dependencias anteriormente remarcadas.

### 3. Desarrollo :computer:

En esta parte del proyecto, implementaremos una serie de tests para que nuestra aplicación pueda detectar errores de programación y corregirlos. Estos sobreviven a nuevas implementaciones y se irȧn agregando nuesvos al crear nuevos features para la computadora.

#### TDD

El Test-Driven Development (TDD) es una estrategia utilizado en cualquier metodología agil de desarrollo, pues esta provee de un mecanismo de testeo continuo. Esto implica escribir tests automáticos para nuestro código, lo cual erae ciertas ventajas: 

*  Advierte al desarrollador de funcionalidades rotas al implementar un nuevo feature al correr nuevamente el ciclo de código.
*  Garantiza un flujo con poca posibilidad de bugs no detectados (especialmente útil al presentar versiones preliminares de una app).
*  Ahorra tiempo, debido a que previene de estar desarrollando parches para errores no previstos.

#### Lineamientos

1. Deben existir al menos 5 test unitarios que corran en la JVM, sin dependencia del framework de Android. Si no existe una clase que pueda ser testeada, crearla. 
2. Deben existir al menos 2 tests que involucren el uso del framework de android en un test unitario local (no instrumentado).
3. Debe de existir al menos 4 tests de integración instrumentados (funciones simples como interactuar con la UI y verificar que una acción esté funcionando correctamente).

#### Tips

* Escribe primero los tests antes que el código, esto asegurará que la calidad de la funcionalidad desarrollada sea alta y sabremos que el código está completo cuando pase el test.
* Desarrolla primero los tests unitarios, después los de integración y finalmente los de end-to-end.

[`Anterior`](../Ejemplo-03) | [`Siguiente`](../Readme.md)      

</div>

