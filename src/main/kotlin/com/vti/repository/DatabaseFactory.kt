package com.vti.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect(hikari())


    }


    fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = System.getenv("DB_DRIVER") // 1
        config.jdbcUrl = System.getenv("DB_URL") // 2
        config.username = System.getenv("DB_USER") // 3
        config.password = System.getenv("DB_PASSWORD") // 4
        config.maximumPoolSize = System.getenv("DB_MAX_POOL_SIZE").toInt() // 5
        config.isAutoCommit = System.getenv("DB_AUTO_COMMIT").toBoolean() // 6
        config.transactionIsolation = System.getenv("DB_TRANSACTION_ISOLATION") // 7
        config.validate()

        return HikariDataSource(config)
    }


    // giai quyet van de block main thread
    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}