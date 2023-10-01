# Pós-Tech-FIAP/ALURA-Fase01-Spring-01

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

# Descrição do projeto
Repositório do projeto do desafio Spring I da pós tech da FIAP/ALURA. Desenvolvimento de uma API de rede social utilizando o framework Spring em Java.

## Requisitos:
1. A API deve ser desenvolvida em Java, utilizando o framework Spring.
2. A API deve permitir o cadastro de usuários, incluindo informações como nome, email e senha.
3. Os usuários deve ser capazes de criar posts, que devem incluir um título, conteúdo e uma lista de tags.
4. Os usuários devem poder curtir os posts de outros usuários.
5. Os usuários devem poder comentar nos posts de outros usuários.
6. A API deve permitir a listagem de todos os posts, com informações dos usuários que criaram, número de curtidas e comentários.
7. A API deve fornecer endpoints para buscar posts por tags.
8. A API deve ser segura, exigindo autenticação para realizar operações de criação de posts, curtidas e comentários.

## Critérios de avaliação:
1. Funcionalidade completa e correta da API, incluindo todas as operações solicitadas.
2. Utilização adequada do framework Spring, seguindo as melhores práticas de desenvolvimento.
3. Estruturação do código, incluindo organização de pacotes e classes.
4. Clareza e legibilidade do código.
5. Utilização de boas práticas de segurança, como authenticação e autorização adequadas.
6. Tratamento adequado de erros e exceções.

## Dicas:
1. Utilize as anotações do Spring Framework, como '@RestController' para definir os endpoints da API e '@Service' para implementar a lógica do negócio.
2. Utilize o Spring Security para a autenticação e autorização dos usuários.
3. Utilize uma biblioteca de persistência de dados, como Spring Data JPA, para acessar o banco de dados.
4. Faça testes unitários para garantir a corretude e robustez de sua API.

# Tecnologias utilizadas
1. Java 17
2. Spring Boot 3.1.2
3. Spring Web MVC
4. Spring Data JPA
5. Lombok
6. Postgres 15.1
7. Flyway
8. JUnit
