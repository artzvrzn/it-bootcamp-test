insert into app_user (id, family_name, given_name, middle_name, email, role)
values (random_uuid(), 'Egorov', 'Anton', 'Sergeyevich', 'egorov.anton@email.com', 'ADMINISTRATOR'),
       (random_uuid(), 'Serova', 'Valentina', '', 'serova@gmail.com', 'SALE_USER'),
       (random_uuid(), 'German', 'Alexey', 'Igorevich', 'ger.alex@rambler.ru', 'CUSTOMER_USER'),
       (random_uuid(), 'Zavadskiy', 'Sergey', '', 'zavadskiy1337@yandex.ru', 'SECURE_API_USER'),
       (random_uuid(), 'Doe', 'John', '', 'john.doe1984@gmail.com', 'ADMINISTRATOR'),
       (random_uuid(), 'Ferguson', 'Alex', '', 'ferguson1988@gmail.com', 'ADMINISTRATOR'),
       (random_uuid(), 'Smirnov', 'Leonid', 'Natalievich', 'ls99@gmail.com', 'ADMINISTRATOR'),
       (random_uuid(), 'Shirokaya', 'Zoya', 'Igorevna', 'zoya@gmail.com', 'ADMINISTRATOR'),
       (random_uuid(), 'Dmitriev', 'Artem', '', 'art.dmtr@gmail.com', 'ADMINISTRATOR'),
       (random_uuid(), 'Strykalo', 'Valentin', '', 'valya@gmail.com', 'ADMINISTRATOR'),
       (random_uuid(), 'Tarasov', 'Maksim', 'Andreevich', 'taras@gmail.com', 'ADMINISTRATOR'),
       (random_uuid(), 'Tolstoy', 'Lev', '', 'lev.tolstoy@gmail.com', 'ADMINISTRATOR');
