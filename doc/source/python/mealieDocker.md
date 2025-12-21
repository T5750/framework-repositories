# Mealie Docker

Mealie is a self hosted recipe manager and meal planner with a RestAPI backend and a reactive frontend application built in Vue for a pleasant user experience for the whole family. Easily add recipes into your database by providing the url and mealie will automatically import the relevant data or add a family recipe with the UI editor

## Demo
[View Demo](https://demo.mealie.io/)

## Docker
```sh
docker run -d --name mealie -p 9925:80 -v $PWD/mealie:/app/data/ hkotel/mealie
```
- [http://localhost:9925/](http://localhost:9925/)
- User: changeme@email.com / MyPassword

## Docker Compose
### Docker Compose with SQLite
```
version: "3.1"
services:
  mealie:
    container_name: mealie
    image: hkotel/mealie:latest
    restart: always
    ports:
      - 9925:80
    environment:
      PUID: 1000
      PGID: 1000
      TZ: America/Anchorage
      # Default Recipe Settings
      RECIPE_PUBLIC: 'true'
      RECIPE_SHOW_NUTRITION: 'true'
      RECIPE_SHOW_ASSETS: 'true'
      RECIPE_LANDSCAPE_VIEW: 'true'
      RECIPE_DISABLE_COMMENTS: 'false'
      RECIPE_DISABLE_AMOUNT: 'false'
      # Gunicorn
      # WEB_CONCURRENCY: 2
      # WORKERS_PER_CORE: 0.5
      # MAX_WORKERS: 8
    volumes:
      - ./mealie/data/:/app/data
```

### Docker Compose with Postgres (BETA)
```
version: "3.1"
services:
  mealie:
    container_name: mealie
    image: hkotel/mealie:latest
    restart: always
    ports:
      - 9090:80
    depends_on:
      - postgres
    environment:
      PUID: 1000
      PGID: 1000
      TZ: America/Anchorage
      # Database Settings
      DB_ENGINE: postgres # Optional: 'sqlite', 'postgres'
      POSTGRES_USER: mealie
      POSTGRES_PASSWORD: mealie
      POSTGRES_SERVER: postgres
      POSTGRES_PORT: 5432
      POSTGRES_DB: mealie
      # Default Recipe Settings
      RECIPE_PUBLIC: 'true'
      RECIPE_SHOW_NUTRITION: 'true'
      RECIPE_SHOW_ASSETS: 'true'
      RECIPE_LANDSCAPE_VIEW: 'true'
      RECIPE_DISABLE_COMMENTS: 'false'
      RECIPE_DISABLE_AMOUNT: 'false'
      # Gunicorn
      # WEB_CONCURRENCY: 2
      # WORKERS_PER_CORE: 0.5
      # MAX_WORKERS: 8
    volumes:
      - ./mealie/data/:/app/data
  postgres:
    container_name: postgres
    image: postgres
    restart: always
    environment:
      POSTGRES_PASSWORD: mealie
      POSTGRES_USER: mealie
```

## Screenshots
![](https://hay-kot.github.io/mealie/assets/img/home_screenshot.png)

## References
- [Mealie Docker](https://hay-kot.github.io/mealie/documentation/getting-started/install/)
- [Mealie GitHub](https://github.com/hay-kot/mealie)