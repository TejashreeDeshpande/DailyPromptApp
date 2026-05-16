# Feature Prompt: Flow Combine — Live Search with Filters

**Difficulty:** Medium  
**Time limit:** 40 minutes  
**Key concepts:** `StateFlow`, `combine`, `debounce`, `flatMapLatest`, cold vs hot flows

---

## Goal

Build a search screen that filters a list of items by both a text query **and** a category chip filter, combining both flows reactively.

---

## Requirements

### Functional
- Display a hardcoded list of 15–20 items, each with a `name: String` and `category: String` (e.g. "Food", "Tech", "Sports")
- A `TextField` for live text search
- A row of category chips (All, Food, Tech, Sports…) — only one active at a time
- Results update immediately as either the query or selected category changes
- Show "No results" when the filtered list is empty

### UI
- Search bar at top
- Horizontal scrollable chip row below it
- `LazyColumn` of results
- Item count shown as a subtitle (e.g. "12 results")

---

## ViewModel API to implement

```kotlin
class SearchFilterViewModel : ViewModel() {
    val query: StateFlow<String>
    val selectedCategory: StateFlow<String>   // "All" means no filter
    val results: StateFlow<List<Item>>        // derived via combine

    fun onQueryChange(q: String)
    fun onCategorySelected(category: String)
}
```

---

## Key implementation detail

```kotlin
results = combine(query, selectedCategory) { q, cat ->
    allItems
        .filter { cat == "All" || it.category == cat }
        .filter { it.name.contains(q, ignoreCase = true) }
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), allItems)
```

---

## Hints

- `combine` takes two (or more) `Flow`s and emits whenever **either** upstream emits
- `stateIn` converts a cold `Flow` into a hot `StateFlow` the UI can collect
- `SharingStarted.WhileSubscribed(5_000)` keeps the flow alive for 5 s after the last subscriber (survives config change)
- Add `debounce(300)` on the query flow before combining to avoid filtering on every keystroke

---

## Stretch goals

- Add a sort toggle (A→Z / Z→A) and combine a third `StateFlow` into the chain
- Replace `debounce` with a `conflate` and explain the difference
- Unit-test the ViewModel using `Turbine`
