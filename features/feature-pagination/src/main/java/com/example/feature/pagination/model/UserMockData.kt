package com.example.feature.pagination.model

object UserMockData {
    private const val PAGE_SIZE = 15
    const val TOTAL_PAGES = 10
    private const val TOTAL_ITEMS = PAGE_SIZE * TOTAL_PAGES

    private val CITIES = listOf(
        "San Francisco",
        "New York",
        "Chicago",
        "Seattle",
        "Austin",
        "Boston",
        "Denver",
        "Los Angeles",
        "Miami",
        "San Jose"
    )

    private val allUsers: List<User> = List(TOTAL_ITEMS) { index ->
        val id = (index + 1).toString()
        User(
            id = id,
            name = "User $id",
            city = CITIES[index % CITIES.size]
        )
    }

    fun getPage(page: Int): UserResponse {
        val safePage = page.coerceIn(1, TOTAL_PAGES)

        val fromIndex = (safePage - 1) * PAGE_SIZE
        val toIndex = (fromIndex + PAGE_SIZE).coerceAtMost(allUsers.size)

        return UserResponse(
            page = safePage,
            pageSize = PAGE_SIZE,
            totalPages = TOTAL_PAGES,
            totalItems = TOTAL_ITEMS,
            users = allUsers.subList(fromIndex, toIndex)
        )
    }
}
