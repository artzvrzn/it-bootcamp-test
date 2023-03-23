<ul>
<li>Java 11</li>
<li>Log4j2</li>
<li>Spring Boot 2</li>
<li>Hibernate 5</li>
<li>MySQL 8</li>
</ul>

<p>docker compose в рут папке</p>
<p>написаны юнит и интергационные тесты</p>
<p>использован Liquibase</p>


Endpoints:
<ul>
<li>POST /user - принимает json:
  {"family_name": "abc",
   "given_name": "abc",
   "middle_name": "", 
   "email": "email@email.com",
   "role": "secure api user"};</li>
<li>GET /users - вернет json список всех пользователей;</li>
<li><GET /users/{page} - @page - номер страницы (нумерация начинается с 1)вернет json объект с 10 пользователями и данными для пагинации</li>
</ul>
