-Java 11
-Log4j2
-Spring Boot 2
-Hibernate 5
-MySQL 8

docker compose в рут папке
написаны юнит и интергационные тесты
использован Liquibase

endpoints:
POST /user - принимает json:
  {"family_name": "abc",
   "given_name": "abc",
   "middle_name": "", 
   "email": "email@email.com",
   "role": "secure api user"}
GET /users - вернет json список всех пользователей;
GET /users/{page} - @page - номер страницы (нумерация начинается с 1)
                    вернет json объект с 10 пользователями и данными для пагинации
                    
