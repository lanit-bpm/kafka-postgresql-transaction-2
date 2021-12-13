# kafka-postgresql-transaction-2
Реализация распределенных транзакций в парадигме strong consistency, охватывающих Kafka и реляционную СУБД (PostgreSQL).

В данном проекте реализован вариант без (deprecated) ChainedTransactionManager

В проекте используются testcontainers для Kafka и PostgreSQL, поэтому для запуска нужен Docker