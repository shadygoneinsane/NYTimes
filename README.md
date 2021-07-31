# NYTimes app

A Simple application which uses the [NY Times Most popular Articles API][NYTimesAPI] and shows a list of articles that shows details when items on the list are tapped.

## Implemented things

* Repository is written in `Kotlin` and is based on `MVVM architectural pattern`
* Dependency Injection is implemented using `Dagger2`
* Data binding using `Data Binding`
* Unit testing is achieved with `JUnit`, `Mockito`, `PowerMockito` and `MockWebServer`

## Application Architecture

* Application follows a single-activity architecture, while using the `Navigation component` for managing fragment transactions.
* Android architecture components, along with `Kotlin Coroutines` for a robust design which is highly testable and maintainable.
* Model-View-ViewModel (MVVM) for separation of development of user interface and its flow of data models.
* SOLID design principles are followed which separates the app into three modules `app`, `domain` and `data` to make the design more understandable, flexible and maintainable in terms of product sustainability.

##Screen Shots
<img src="https://github.com/shadygoneinsane/NYTimes/blob/master/screenshots/master.png" alt="Master Screen"/>
<img src="https://github.com/shadygoneinsane/NYTimes/blob/master/screenshots/detail.png" alt="Detail Screen"/>

##Build
On Terminal use the below command for generating debug build:
```./gradlew assembleDebug```

On Terminal use the below command for generating release build:
``` ./gradlew assembleRelease ```

### Tech Stack used
* [Android Support Library][support-lib]
* [Android Architecture Components][arch]
* [Android Data Binding][data-binding]
* [Android Naviation Component][navigation]
* [Kotlin Coroutines][coroutines]
* [Dagger 2][dagger2] for dependency injection
* [Retrofit][retrofit] for REST api communication
* [Glide][glide] for image loading
* [Espresso][espresso] for Android UI tests
* [Mockito][mockito] for evaluating app's logic using local unit tests
* [MockWebServer][mockwebserver] for testing HTTP clients


[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[support-lib]: https://developer.android.com/topic/libraries/support-library/index.html
[arch]: https://developer.android.com/arch
[data-binding]: https://developer.android.com/topic/libraries/data-binding/index.html
[dagger2]: https://google.github.io/dagger
[retrofit]: http://square.github.io/retrofit
[glide]: https://github.com/bumptech/glide
[espresso]: https://developer.android.com/training/testing/espresso
[mockito]: https://site.mockito.org/
[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[NYTimesAPI]: https://developer.nytimes.com/
[navigation]: https://developer.android.com/guide/navigation/navigation-getting-started
[coroutines]: https://kotlinlang.org/docs/coroutines-guide.html