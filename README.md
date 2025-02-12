# ORDER
Aplicações para controle e disponibilização dos cálculos dos pedidos

Este projeto é dividido em módulos gradle para desacoplar as responsabilidades

Módulos:
- API: Responsavel por disponibilizar os endpoints para consulta dos pedidos e seus items
- RABBIT-CONSUMER: Responsavel por receber os pedidos para calcularmos os seus valores

LIB's:
- CORE: Lib responsavel por centralizar a regra de negócio e configurações comuns entre os módulos de aplicação

## Subindo container's local

Para criar os containers:
```
docker-compose -f docker/docker-compose.yml up
```

Apos a criação dos container basta usar o comando
```
local-env-start
local-env-stop
```
Para subir e baixar os containers respectivamente

# Testes
Para a execução dos testes temos os seguintes comandos:
```
make clean-build-test
```
Para rodar os testes de todas as aplicações

```
make clean-build-test app=${app} (order-api | order-rabbit-consumer)
```
Para rodar os testes de um modulo específico

# Makefile
O [Makefile](Makefile) é uma ferramenta de automação de tarefas, ele permite definir regras para construir,
testar, limpar e executar projetos de maneira automatizada, especificando alvos, dependências e comandos a serem executados.

Os comandos aqui configurados são [Makefile](Makefile):
- migration: Comando para rodar o ‘step’ de migrações do banco de dados
- clean-build-test: Comando para rodas os teste unitários
- local-env-start: Sobe todos os containers necessários para rodar as aplicações localmente
- local-env-stop: Desce todos os containers que foram iniciados pelo comando 'local-env-start' 