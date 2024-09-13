# **Pokémon App**
Una aplicación móvil para mostrar una lista de Pokémon y sus detalles, desarrollada en Kotlin con Jetpack Compose para Android. La aplicación utiliza la API pública de Pokémon para obtener la información de los Pokémon y permite a los usuarios marcar Pokémon como favoritos.

## **Características**
Pantalla Principal: Muestra una lista de Pokémon en un diseño de cuadrícula con imágenes y nombres.
Pantalla de Detalles: Muestra detalles completos de un Pokémon seleccionado, incluyendo nombre, altura, peso y tipo.
Favoritos: Permite a los usuarios marcar Pokémon como favoritos y ver un icono en la lista si el Pokémon está marcado como favorito.
Paginación: Carga más Pokémon a medida que el usuario desplaza hacia abajo en la lista.

### **Tecnologías Utilizadas**
Kotlin: Lenguaje de programación principal.
Jetpack Compose: Framework de UI declarativa de Google para Android.
Room: Biblioteca para persistencia de datos en SQLite.
Retrofit: Biblioteca para la comunicación con la API.
Hilt: Biblioteca para la inyección de dependencias.
Coroutines: Para la programación asíncrona.

#### **Estructura del Proyecto**
El proyecto está estructurado en tres capas principales:
Data: Maneja la recuperación y almacenamiento de datos.
Local: Implementación de la base de datos usando Room.
Remote: Comunicación con la API de Pokémon.
Repository: Implementación de las interfaces para obtener los datos.
Domain: Contiene la lógica de negocio y las interfaces de repositorio.
Model: Modelos de datos que representan entidades del dominio.
Repository: Interfaz para el repositorio.
UseCase: Casos de uso para obtener y manipular datos.
Presentation: Maneja la UI de la aplicación.
Screens: Composables para las diferentes pantallas (lista de Pokémon, detalles de Pokémon).
ViewModel: Contiene la lógica de presentación y maneja el estado de la UI.

##### **Instalación**
Clona el repositorio:

bash
Copiar código
git clone https://github.com/Alan-Bp/PokemonTest.git
cd "repositorio"
Abre el proyecto en Android Studio.

Sincroniza el proyecto con Gradle.

Ejecuta la aplicación en un emulador o dispositivo Android.

###### **Uso**
Pantalla Principal: Navega a la pantalla principal para ver la lista de Pokémon. Puedes tocar en cualquier Pokémon para ver sus detalles.
Pantalla de Detalles: En la pantalla de detalles, puedes ver información detallada sobre el Pokémon seleccionado y alternar su estado de favorito.
Contribuciones
Haz un fork del repositorio.
Crea una rama para tus cambios:
bash
Copiar código
git checkout -b nombre-de-tu-rama
Realiza tus cambios y confírmalos:
bash
Copiar código
git add .
git commit -m "Descripción de los cambios"
Sube tu rama al repositorio remoto:
bash
Copiar código
git push origin nombre-de-tu-rama
Crea un Pull Request desde tu rama a master.
Licencia
Este proyecto es un Test sin licencia.

