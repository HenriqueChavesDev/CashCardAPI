# CashCard API

![Java Version](https://img.shields.io/badge/Java-17-blue)
![Spring Web](https://img.shields.io/badge/Spring%20Web-5.3.9-brightgreen)
![Gradle](https://img.shields.io/badge/Gradle-7.2-blueviolet)

A Family Cash Card API é uma aplicação que fornece uma maneira moderna para os pais gerenciarem os fundos de mesada para seus filhos. Ao invés de lidar com dinheiro físico, nossa API baseada em Java 17 e Spring Web permite que os pais criem e gerenciem "cartões de mesada" virtuais para seus filhos. Esses cartões de mesada se assemelham a cartões-presente e permitem que as crianças tenham um saldo disponível para uso em compras ou outras atividades aprovadas pelos pais.

Com a Family Cash Card API, os pais têm total controle sobre a mesada de seus filhos. Eles podem facilmente adicionar fundos aos cartões, definir limites de gastos, acompanhar transações e receber notificações em tempo real sobre o uso dos cartões. Além disso, as crianças podem visualizar o saldo do cartão e seu histórico de transações, promovendo uma maior conscientização sobre suas finanças.

## Funcionalidades Principais

- Criação de cartões de mesada virtuais para cada filho.
- Adição de fundos aos cartões de mesada.
- Definição de limites de gastos para cada cartão.
- Acompanhamento em tempo real de transações e notificações para os pais.
- Visualização de saldo e histórico de transações para as crianças.
- Interface de API amigável e intuitiva para facilitar a integração com aplicativos front-end.

## Tecnologias Utilizadas

- Java 17: Linguagem de programação para a construção da API.
- Spring Web: Framework para desenvolvimento de aplicativos web baseados em Spring.
- JUnit: Framework para testes unitários automatizados.
- Gradle: Gerenciador de dependências e build do projeto.

## Como Executar

1. Clone este repositório para o seu sistema local.
    ```bash
        git clone https://github.com/HenriqueChavesDev/CashCardAPI.git
    ```
2. Navegue até o diretório raiz do projeto.
    ```bash
    cd CashCardAPI
   ```
3. Execute o seguinte comando para compilar o projeto e executar os testes:
    ```bash
    ./gradlew build
    ```
4. Após a compilação bem-sucedida, inicie a aplicação com o seguinte comando:
    ```bash
   ./gradlew bootRun
    ```