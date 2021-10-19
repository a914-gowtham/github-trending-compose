package com.compose.template.models

import kotlinx.serialization.Serializable

@Serializable
data class BuiltBy(
    val avatar: String,
    val href: String,
    val username: String
)