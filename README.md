# CityBike Viewer

CityBike Viewer este o aplicație Android dezvoltată în Kotlin, care permite utilizatorilor să exploreze rețelele de biciclete din diverse orașe, să afle detalii despre stații (inclusiv starea vremii) și să își salveze rețelele preferate pentru acces rapid.

## Funcționalități

* **Lista de rețele:** Vizualizarea unei liste cu rețelele de biciclete disponibile, incluzând numele, orașul și țara.
* **Detalii stație și Vreme:** Afișarea detaliilor unei rețele specifice (coordonate GPS) și a vremii curente la locația respectivă (temperatură, condiții meteo, umiditate).
* **Favorite:** Posibilitatea de a salva și șterge rețelele preferate într-o bază de date locală, accesibilă dintr-un ecran dedicat.

## Tehnologii și Arhitectură utilizate

Aplicația folosește arhitectura **MVVM (Model-View-ViewModel)** și cele mai bune practici de dezvoltare Android moderne:

* **[Kotlin](https://kotlinlang.org/)** - Limbajul principal de programare.
* **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Pentru construirea interfeței grafice (UI) într-un mod declarativ.
* **[Dagger Hilt](https://dagger.dev/hilt/)** - Pentru Dependency Injection (Injectarea Dependențelor).
* **[Retrofit](https://square.github.io/retrofit/) & Gson** - Pentru realizarea cererilor de rețea (API calls) și parsarea datelor JSON.
* **[Room Database](https://developer.android.com/training/data-storage/room)** - Pentru stocarea locală persistentă a stațiilor favorite.
* **[Coil](https://coil-kt.github.io/coil/compose/)** - Pentru încărcarea eficientă a imaginilor (ex: iconițele pentru starea vremii).
* **Coroutines & Flow** - Pentru programare asincronă și gestionarea reactivă a stărilor UI (`StateFlow`).
* **Jetpack Navigation Compose** - Pentru navigarea între ecranele aplicației.

## Instalare și Configurare

1.  **Clonează repository-ul:**
    ```bash
    git clone [https://github.com/flaviu-petre/citybike-station-viewer.git](https://github.com/flaviu-petre/citybike-station-viewer.git)
    ```
2.  **Deschide proiectul** în Android Studio (Versiunea recomandată: compatibilă cu AGP 8.13.1 și Kotlin 2.0.21).
3.  **Configurarea cheii API pentru Vreme:**
    Aplicația folosește [WeatherAPI](https://www.weatherapi.com/) pentru a obține datele meteo.
    * Creează un cont gratuit pe WeatherAPI și obține o cheie API.
    * Navighează în proiect la fișierul `app/src/main/java/com/example/citybikeviewer/data/WeatherRepository.kt`.
    * Înlocuiește textul `YOUR_API_KEY_HERE` cu cheia ta API:
        ```kotlin
        private val API_KEY = "CHEIA_TA_API_AICI"
        ```
4.  **Rulează aplicația** pe un emulator sau pe un dispozitiv fizic (Cerințe minime: Android 7.0 Nougat - API 24).

## Structura Proiectului

* `data/`
    * `local/`: Implementarea bazei de date Room (`AppDatabase`, `FavoriteDao`, entități).
    * `remote/`: Interfețele Retrofit pentru cererile de rețea (`CityBikeApi`, `WeatherApi`).
    * `model/`: Clasele de date (Data classes) utilizate pentru parsarea răspunsurilor API.
    * `CityBikeRepository` & `WeatherRepository`: Clasele responsabile cu furnizarea datelor către ViewModel.
* `ui/`
    * `screens/`: Ecranele aplicației construite cu Compose (`NetworkListScreen`, `NetworkDetailScreen`, `FavoritesScreen`).
    * `theme/`: Configurarea temei, culorilor și a tipografiei (`Color.kt`, `Theme.kt`, `Type.kt`).
    * `CityBikeViewModel.kt`: Gestionează logica de business și expune stările UI (`CityBikeUiState`, `WeatherUiState`).

## Licență

Acest proiect este licențiat sub [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
