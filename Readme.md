# Тестовое задание 

## Реализация API для сервиса

Методы описаны в файле "methods.xlsx"

При запуске приложения в базу данных PostgreSQL сохраняются User с данными: 
```
  User 1 {
    int id = 1;
    String name = "Alexey";
    String email = "alexey@gmail.com";
    String password = "0000" (encoded);
    double balance = 120;
    String role = "ADMIN";
  }

  User 2 {
    int id = 2;
    String name = "Sergey";
    String email = "sergey@gmail.com";
    String password = "1111" (encoded);
    double balance = 120;
    String role = "USER";
  }
    
```