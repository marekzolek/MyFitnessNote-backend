# MyFitnessNote-backend
> Docelowo aplikacja służąca do monitorowania swojej sylwetki, diety, progresu siłowego

## Spis treści
* [Informacje](#general-info)
* [Technologie](#technologies)
* [Status](#status)

## Informacje

Aplikacja jest trakcie tworzenia. Przy tym projekcie zamierzam użyć Spring Security do logowania, które jest obecnie zaimplementowane. W pierwszej fazie zamierzam zrobić proces logowania oraz rejestracji użytkownika. Rejestracja użytkownika będzie polegała na wypełnieniu odpowiednich pól wraz z email użytkownika po czym użytkownik otrzyma link z tokenem w celu aktywacji konta.

Kolejnym krokiem, będzie dodanie danych użytkownika(wzrost, waga, wiek itp) - docelowo o te dane ma pytać po pierwszym zalogowaniu.

Następnie kalkulator zapotrzebowania kalorycznego w zależności od oczekiwań użytkownika(czy chce przytyć czy schudnąć)

Aplikacja docelowa będzie miała za zadanie dodawanie posiłków w ciągu dnia i obliczanie dziennego bilansu kalorycznego, monitorowanie progresu siłowego, wszelkie dane chciałbym pokazywać nie tylko za pomocą liczb ale również za pomocą wykresów. 

Do aplikacji dołączone zostanie również projekt frontendowy, dwa projekty mają się ze sobą komunikować.

Celem tej aplikacji jest poznanie zaawansowanych narzędzi Spring Boot, zapoczątkowanie nauki frontendu a także dbanie o czytelny kod.

Z uwagi na pewne doświadczenie zdecydowałem się również na zastosowanie ciągłej itegracji, w tym celu zamierzam użyć CirlceCI, Heroku, Docker

## Technologie
* Java
* Spring Boot
* Spring Security
* PostgreSQL database
* Heroku
* CircleCi
* Docker

## Status
Projekt jest w fazie początkowej.
