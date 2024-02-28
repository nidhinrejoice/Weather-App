# Weather App

This Kotlin-based Android application leverages two open-source weather APIs to fetch weather information based on the current location of the device. The user interface is built using Jetpack Compose, while the app architecture follows the MVVM (Model-View-ViewModel) and Clean Architecture principles.

## **Features**
* Fetches weather information using two open-source weather APIs.
* Utilizes Jetpack Compose for a modern and declarative UI.
* Demonstrates switching between weather APIs using dependency injection.
* Implements SOLID principles for better code maintainability and extensibility.
* Seamlessly switches the weather source in the data layer based on user inputs.
* Ensures that the domain and presentation layers remain agnostic to the source of the weather API.
* 

**Architecture Overview**
The app architecture follows the MVVM (Model-View-ViewModel) pattern and Clean Architecture principles. It is divided into the following layers:

* Presentation Layer: Contains UI components implemented with Jetpack Compose. It interacts with the ViewModel to display weather information to the user.
* 
* Domain Layer: Defines business logic and use cases. It remains independent of the source of weather data and is responsible for orchestrating data flow between the presentation and data layers.
* 
* Data Layer: Handles data retrieval from external sources such as APIs. It seamlessly switches between weather APIs based on user preferences. This layer ensures that the presentation and domain layers remain unaware of the specific weather API implementation details.

## **Dependencies**

* Jetpack Compose: For building the modern UI.
* Kotlin Coroutines: For asynchronous and non-blocking programming.
* Dagger Hilt: For dependency injection to facilitate switching between weather APIs.
* Retrofit: For making network requests to fetch weather data from APIs.