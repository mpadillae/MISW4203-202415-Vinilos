# Vinilos App

Este proyecto es una aplicación Android desarrollada utilizando Kotlin. Utiliza Retrofit para operaciones de red y sigue el patrón de arquitectura MVVM. Este proyecto ha sido desarrollado como parte del curso MISW4203 - Ingeniería de software para aplicaciones móviles de la Universidad de los Andes.

## Integrantes

| Nombre                 | Correo                       |
|------------------------|------------------------------|
| Miguel Fernando Padilla Espino | m.padillae@uniandes.edu.co |
| Johann Sebastian Páez Campos | js.paezc1@uniandes.edu.co |
| Juan Sebastian Cervantes Restrepo | js.cervantes@uniandes.edu.co |
| Julian Esteban Oliveros Forero | je.oliverosf@uniandes.edu.co |

## Requisitos previos
Para levantar este proyecto necesitarás:
- Android Studio Ladybug | 2024.2.1 o superior.
- SDK de Android.
- Variable de entorno `ANDROID_HOME` apuntando al SDK.
- JDK 11 o superior.
- Gradle 7.0 o superior.

## Estructura del proyecto

```
📦 MISW4203-202415-Vinilos
├── 📁 app
│   ├── 📁 src
│   │   ├── 📁 androidTest
│   │   ├── 📁 main
│   │   │   ├── 📁 java
│   │   │   │   └── 📁 com.mfpe.vinilos
│   │   │   │       ├── 📁 adapters
│   │   │   │       ├── 📁 data
│   │   │   │       │   ├── 📁 model
│   │   │   │       │   ├── 📁 network
│   │   │   │       │   └── 📁 repository
│   │   │   │       ├── 📁 ui
│   │   │   │       │   ├── 📁 albums
│   │   │   │       │   ├── 📁 artist
│   │   │   │       │   ├── 📁 collectors
│   │   │   │       │   └── 📁 shared
│   │   │   │       ├── 📁 utils
│   │   │   │       └── 📁 viewmodel
│   │   │   └── 📁 res
│   │   └── 📁 test
│   └── 📄 build.gradle
├── 📄 build.gradle
├── 📄 settings.gradle
└── 📄 README.md
```

## Compilación del proyecto
1. Abre el proyecto en Android Studio.
2. Sincroniza el proyecto con los archivos de Gradle haciendo clic en `File > Sync Project with Gradle Files` en la barra de menú.
3. Compila el proyecto seleccionando `Build > Make Project` en la barra de menú.

## Generar APK
1. En Android Studio, ve a `Build > Build Bundle(s) / APK(s) > Build APK(s)`.
2. El APK se generará y lo encontrarás en el directorio `app/build/outputs/apk/`.

## Ejecutar la aplicación
1. Conecta un dispositivo Android vía USB o inicia un emulador de Android.
2. Haz clic en el botón `Run` en la barra de herramientas o selecciona `Run > Run 'app'` en el menú.
3. Elige el dispositivo de destino y haz clic en `OK`.

## Ejecución de pruebas
### Pruebas unitarias
1. Para ejecutar pruebas unitarias, ve a `Run > Edit Configurations`.
2. Haz clic en `+` para agregar una nueva configuración y selecciona `JUnit` de la lista.
3. Ponle un nombre. En este caso: `Unit tests`.
4. Selecciona el módulo `vinilos.app.unitTest`.
5. Establece el `Test kind` en `All in package`.
6. Haz clic en `Apply` y luego en `OK`.
7. Ejecuta las pruebas seleccionando `Run > Run 'Unit tests'`.

![image](https://i.ibb.co/Nm3vkV1/Captura-de-pantalla-2024-10-24-a-la-s-10-49-52.png)
También se pueden ejecutar las pruebas directamente haciendo clic derecho sobre el paquete y seleccionando la opción `Run 'Tests in vinilos.app.UnitTest'`.

<p align="center">
  <img src="https://github.com/user-attachments/assets/cea232e8-c242-4479-b609-41858addba80" alt="Picture" width="564" height="507"  />
</p>

### Pruebas con Espresso
1. Conecta un dispositivo Android vía USB o inicia un emulador de Android.
2. Ve a `Run > Edit Configurations`.
3. Haz clic en `+` para agregar una nueva configuración y selecciona `Android Instrumented Tests` de la lista.
3. Ponle un nombre. En este caso: `Espresso tests`.
4. Establece el `Module` en el módulo de la aplicación `(vinilos.app.androidTest)`.
6. Haz clic en `Apply` y luego en `OK`.
7. Ejecuta las pruebas seleccionando `Run > Run 'Espresso tests'`.

![image](https://i.ibb.co/8gG3bNP/Captura-de-pantalla-2024-10-24-a-la-s-10-57-44.png)
También se pueden ejecutar las pruebas directamente haciendo clic derecho sobre el paquete y seleccionando la opción `Run 'All tests'`.

<p align="center">
  <img src="https://github.com/user-attachments/assets/046b443e-ca2d-47a4-b113-fdb91bbb8252" alt="Picture" width="529"   />
</p>


## Dependencias
El proyecto utiliza las siguientes dependencias:
- Retrofit
- Gson
- Glide
- Material Rating Bar
- Bibliotecas AndroidX
- Android Jetpack LiveData y ViewModel 

## Licencia
Copyright © MISW4203 - Ingeniería de software para aplicaciones móviles - 2024.
