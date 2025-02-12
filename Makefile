SHELL := /bin/bash

# Para executar/reparar as migrações do banco de dados
migration:
	@echo "-- Executing database migrations --"
	./gradlew flywayMigrate

# Para executar os testes da aplicação
clean-build-test:
	@if [ -n "$(app)" ]; then \
		./gradlew :$(app):clean build -Pvalidate --parallel -x test; \
		./gradlew :$(app):test --parallel; \
	else \
	  	./gradlew clean build -Pvalidate --parallel -x test; \
	  	./gradlew test --parallel; \
	fi

# Startar os containers para desenvolvimento local
local-env-start:
	make local-db-start
	make local-rabbit-start

# Para os containers usados para desenvolvimento local
local-env-stop:
	make local-db-stop
	make local-rabbit-stop

# Startar o container do banco de dados oracle
local-db-start:
	@echo "-- Starting local database --"
	@docker start oracle-order

# Para o container do banco de dados oracle
local-db-stop:
	@echo "-- Stopping local database --"
	@docker stop oracle-order

# Startar o container do rabbitmq
local-rabbit-start:
	@echo "-- Starting local rabbit --"
	@docker start rabbitmq-order

# Para o container do rabbitmq
local-rabbit-stop:
	@echo "-- Stopping local rabbit --"
	@docker stop rabbitmq-order