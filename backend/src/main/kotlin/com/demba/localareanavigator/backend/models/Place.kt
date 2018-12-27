package com.demba.localareanavigator.backend.models

data class Place(val name: String, val data: String?, val imageURL: String) {

    fun toJson(): String {
        return "{\"name\":\"$name\",\"data\":\"${data?.replace("\"", "\\\"")}\",\"imageURL\":\"$imageURL\"}"
    }
}