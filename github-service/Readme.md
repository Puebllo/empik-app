## Empik App

### Stack:
- Java 17 + Maven
- SpringBoot 3.2.5
- PostgreSQL 16
- Testy: Spring, H2 in mem DB, Junit 5 + Mockito
- Docker

### Uruchomienie aplikacji (Docker)

Żeby uruchomić aplikację, należy w katalogu głównym projektu, uruchomić polecenie:

`docker compose up`

Do wystopowania aplikacji

`docker compose down`

### Obsługa aplikacji

Port aplikacji wystawiony na host, na które można wysyłać zapytania to `8081`

Przykładowe zapytanie:

`localhost:8081/users/puebllo`

Przykładowa odpowiedź:

```json
{
    "id": 22179495,
    "login": "Puebllo",
    "name": "Pueblo",
    "type": "User",
    "avatarUrl": "https://avatars.githubusercontent.com/u/22179495?v=4",
    "createdAt": "2016-09-13T19:01:38",
    "calculations": 102.0
}
```

Port bazy danych PostgreSQL wystawiony na host to `5440`

### Konfiguracja aplikacji

w pliku `.env` znajdują się parametry środowiskowe potrzebne do obsługi aplikacji


`POSTGRES_DB_USER` - nazwa użytkownika PostgreSQL

`POSTGRES_DB_ROOT_PASSWORD`- hasło do użytkownika

`POSTGRES_DB_DATABASE` - nazwa bazy

`POSTGRES_DB_DOCKER_PORT` - wew. port bazy

`POSTGRES_DB_HOST_PORT` - port bazy dla hosta

`APP_DOCKER_PORT` - wew. port aplikacji

`APP_HOST_PORT` - port aplikacji dla hosta

`APP_GITHUB_BASE_URL` - base url do API GitHub


