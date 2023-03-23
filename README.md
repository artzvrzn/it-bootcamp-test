Технологии:
<ul>
<li>Java 11</li>
<li>Log4j2</li>
<li>Spring Boot 2</li>
<li>Hibernate 5</li>
<li>MySQL 8</li>
</ul>

Дополнительно:
<ul>
<li>docker compose в рут папке</li>
<li>написаны юнит и интергационные тесты</li>
<li>использован Liquibase</li>
</ul>

Endpoints:
<ul>
<li>POST /user - принимает json:
  <br>{"family_name": "abc",
  <br>"given_name": "abc",
  <br>"middle_name": "", 
  <br>"email": "email@email.com",
  <br>"role": "secure api user"};</li>
<li>GET /users - вернет json список всех пользователей;</li>
<li>GET /users/{page} - @page - номер страницы (нумерация начинается с 1)
    <br>вернет json объект с 10 пользователями и данными для пагинации</li>
</ul>
