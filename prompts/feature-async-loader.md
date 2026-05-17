# Feature: Async Content Loader

The product team wants to build a screen that loads content asynchronously.

The screen should display a loading spinner while data is being fetched, the content list
once loading succeeds, and a descriptive error message with a retry button if the fetch
fails. Simulate the fetch with a coroutine delay and randomly produce a success or failure
result. The UI should react to each state with no flicker or layout jump.
