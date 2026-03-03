## NASA Picture Service

Сервис получает картинку дня от NASA API и отдает её в браузере

**Технологии**

* Java 17

* Spring Boot 2.7.18

* Spring Cloud OpenFeign (для запросов к NASA API)

* RestTemplate (для скачивания картинок)

* Gradle

* JUnit 5, Mockito, WireMock (тестирование)

### Запуск

1. Получить API ключ на api.nasa.gov

2. В src/main/resources/application.properties: nasa.api.key=ВАШ_КЛЮЧ

3. Запустить командой ./gradlew bootRun или с помощью кнопки в IDE, открыть http://localhost:8080/api/nasa/image

* Запустить тесты можно командой ./gradlew test или с помощью кнопки в IDE

### Внимание
NASA API может быть недоступен из России, при получении ошибки 403 используйте VPN
