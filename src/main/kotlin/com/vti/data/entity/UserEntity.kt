package com.vti.data.entity

import org.jetbrains.exposed.sql.Table

class UserEntity :Table(){
    val email = varchar("email",512)
}