# Feature Prompt: Download Queue with Channel

**Difficulty:** Medium-Hard  
**Time limit:** 40 minutes  
**Key concepts:** `Channel`, `produce`, `consumeEach`, fan-out, backpressure, `BufferOverflow`

---

## Goal

Build a "download queue" screen where the user enqueues items and a single background worker processes them one at a time, updating progress in the UI.

---

## Requirements

### Functional
- Display a list of 8 hardcoded "files" to download (name + size label)
- Each item has an **Enqueue** button — once enqueued it shows "Queued" and the button is disabled
- A background coroutine processes the queue one item at a time:
  - Each download takes 2 seconds (`delay(2_000)`)
  - While downloading, the item shows a `LinearProgressIndicator` animating from 0→1
  - When done, the item shows "✓ Done"
- Show the count of items waiting in the queue (e.g. "3 in queue")

### UI
- `LazyColumn` of file cards
- Each card shows: file name, size, status chip (Idle / Queued / Downloading / Done)
- Progress bar visible only during Downloading state
- "Queue empty" footer message when nothing is waiting

---

## ViewModel API to implement

```kotlin
enum class DownloadStatus { Idle, Queued, Downloading, Done }

data class DownloadItem(
    val id: Int,
    val name: String,
    val size: String,
    val status: DownloadStatus = DownloadStatus.Idle,
    val progress: Float = 0f
)

class DownloadQueueViewModel : ViewModel() {
    val items: StateFlow<List<DownloadItem>>
    val queueSize: StateFlow<Int>

    fun enqueue(id: Int)
}
```

---

## Key implementation detail

```kotlin
private val channel = Channel<Int>(capacity = Channel.UNLIMITED)

init {
    viewModelScope.launch {
        channel.consumeEach { id ->
            // mark Downloading, animate progress, mark Done
        }
    }
}

fun enqueue(id: Int) {
    updateStatus(id, DownloadStatus.Queued)
    viewModelScope.launch { channel.send(id) }
}
```

---

## Hints

- `Channel.UNLIMITED` means `send` never suspends — good for a UI-driven queue
- `consumeEach` processes items sequentially; the coroutine suspends between items waiting for the next
- To animate progress, use a loop: `repeat(20) { delay(100); updateProgress(id, it / 20f) }`
- `Channel` is a **hot** primitive — unlike `Flow`, items sent before a consumer exists are buffered
- `queueSize` can be derived from counting items with `status == Queued`

---

## Stretch goals

- Allow cancelling a queued (not yet downloading) item
- Support parallel downloads (2 at a time) using `repeat(2) { launch { channel.consumeEach {...} } }`
- Replace `Channel.UNLIMITED` with `Channel(capacity = 3, onBufferOverflow = BufferOverflow.DROP_OLDEST)` and observe the behavior
