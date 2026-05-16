# Feature Prompt: Timer

**Difficulty:** Medium  
**Time limit:** 40 minutes  
**Key concepts:** `LaunchedEffect`, `coroutineScope`, `delay`, pause/resume, `isActive`

---

## Goal

Build a countdown timer screen that the user can start, pause, and reset.

---

## Requirements

### Functional
- Display a countdown from a fixed duration (e.g. 60 seconds) in `MM:SS` format
- **Start** begins or resumes the countdown
- **Pause** suspends the countdown without resetting it
- **Reset** stops the countdown and restores the full duration
- When the timer reaches zero, show a "Time's up!" message and disable Start

### UI
- Large centered time display (`MM:SS`)
- Row of buttons: Start / Pause / Reset
- Disable irrelevant buttons (e.g. Pause when already paused)

---

## ViewModel API to implement

```kotlin
data class TimerState(
    val remainingSeconds: Int,
    val isRunning: Boolean,
    val isFinished: Boolean
)

class TimerViewModel : ViewModel() {
    val state: StateFlow<TimerState>

    fun start()
    fun pause()
    fun reset()
}
```

---

## Hints

- Use `viewModelScope.launch { while (isActive) { delay(1_000); decrement } }` for the tick loop
- Cancel the coroutine job to pause; relaunch to resume
- `LaunchedEffect` is **not** needed in the ViewModel — keep coroutines in `viewModelScope`
- Use `LaunchedEffect(state.isFinished)` in the composable if you want a side-effect on finish

---

## Stretch goals

- Let the user input a custom duration via a `TextField`
- Add a progress indicator (`LinearProgressIndicator`) that drains as time passes
- Play a sound or trigger a `Vibrator` when time's up
