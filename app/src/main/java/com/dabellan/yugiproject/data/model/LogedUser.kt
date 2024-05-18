package com.dabellan.yugiproject.data.model

data class LogedUser(
    var userId: Int? = null
) {
    companion object {
        var userId: Int = 0
    }
}
