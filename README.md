# 🎓 Sistema de Gestão Escolar - JPA & Hibernate

Este projeto é a solução para o desafio de digitalização de processos de uma escola de cursos livres. A aplicação é um sistema de console robusto que permite o gerenciamento completo de alunos, cursos e matrículas, utilizando persistência em banco de dados relacional.

## 🚀 Funcionalidades

O sistema foi estruturado em menus e submenus para garantir uma melhor experiência de uso (UX):

- **Gestão de Alunos:** cadastro, listagem, edição de dados e remoção.
- **Gestão de Cursos:** cadastro com carga horária e listagem de formações.
- **Matrículas:** vínculo entre alunos e cursos com registro de data automática.

### Consultas Inteligentes (Submenu)

- Busca de aluno por nome (parcial) ou e-mail exato.
- Busca de curso por nome ou parte do nome.
- Filtro de alunos matriculados por curso específico.

### Relatórios

- Visualização formatada de todas as matrículas ativas.

## 🛠️ Tecnologias Utilizadas

- Java 17+
- JPA (Java Persistence API) com Hibernate
- PostgreSQL (Banco de dados relacional)
- Maven (Gerenciador de dependências)
- IntelliJ IDEA (IDE de desenvolvimento)

## 📋 Requisitos de QA (Diferenciais de Qualidade)

Como foco em qualidade, o sistema implementa:

- **Tratamento de Exceções:** o sistema não encerra ao digitar formatos de data inválidos ou caracteres em campos numéricos.
- **Integridade Referencial:** proteção contra exclusão de alunos que possuem matrículas ativas.
- **Formatação de Dados:** datas exibidas no padrão brasileiro (dd/MM/yyyy) e relatórios alinhados no console.

## ⚙️ Como Executar

### Configuração do Banco

Certifique-se de que o PostgreSQL está rodando.

Crie um banco de dados chamado `escola_db` (ou o nome definido no seu `persistence.xml`).

### Configuração do Persistence

No arquivo `src/main/resources/META-INF/persistence.xml`, ajuste seu usuário e senha do Postgres:

```xml
<property name="jakarta.persistence.jdbc.user" value="seu_usuario" />
<property name="jakarta.persistence.jdbc.password" value="sua_senha" />
```
