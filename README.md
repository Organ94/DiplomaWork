# Дипломная работа "Облочное хранилище"

## Описание проекта

Все запросы авторизованы.

Web-приложение (FRONT) подключается к серверу без доработок.

В Web-приложении (FRONT) реализуется процесс авторизации и загрузки/вывода списка файлов авторизованного пользователя.

### Используемые порты:

- Web-приложение (FRONT) - 8080
- REST-сервис (BACKEND) - 5050

## Стартовые пользователи:

**EMAIL:** user_one@email.user **PASSWORD:** user_one

**EMAIL:** user_two@email.user **PASSWORD:** user_two

## Описание реализации проекта:

- Приложение разработано с использованием Spring Boot;
- Использован сборщик пакетов Maven;
- Использована база данных MySql;
- Использована система управления миграциями Liquibase;
- Для запуска используется docker, docker-compose;
- Код размещен на github;
- Код покрыт unit тестами и добавлены интеграционные тесты с использованием testcontainers;
- Информация о пользователях сервиса и файлов пользователя хранится в базе данных;

### Запуск приложения:

#### запуск FRONT:

1. Установите nodejs (версия не ниже 19.7.0) на компьютер [следуя инструкции](https://nodejs.org/ru/download/);
2. Скачать [FRONT](https://github.com/frepingod/netology-cloud-storage-front) (JavaScript);
3. Перейти в папку FRONT приложения и все команды для запуска выполнять из нее;
4. Следуя описанию README.md FRONT проекта, запустите nodejs-приложение (npm install, npm run serve);
5. Далее нужно задать url для вызова своего backend-сервиса.
    1. В файле .env FRONT (находится в корне проекта) приложения нужно изменить url до backend, например: VUE_APP_BASE_URL=http://localhost:8080.
    - Нужно указать корневой url вашего backend, к нему frontend будет добавлять все пути согласно спецификации
    - Для VUE_APP_BASE_URL=http://localhost:8080 при выполнении логина frontend вызовет http://localhost:8080/login
    2. Запустите FRONT снова: npm run serve.

#### Запуск BACKEND:

1. Скачать данный проект;
2. Запустить `docker-compose.yml` либо через Main метод класса DiplomaWorkApplication.java. (При запуске приложения через метод Main следует запустить базу данных 'docker-compose.yaml -> database').
Автоматически создадутся все необходимые в базе данных таблицы (с двумя стартовыми пользователями в таблице users).
Остальные шаги остаются не изменны.