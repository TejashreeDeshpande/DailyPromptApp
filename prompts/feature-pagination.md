# Feature: Paginated List

The product team wants to build a paginated list feature.

Users should see a list of items loaded from a simulated remote data source that returns
results in pages of 20. As the user scrolls toward the bottom of the list, the next page
should load automatically and be appended to the existing items. A loading indicator should
be displayed at the bottom while new items are being fetched. If loading fails, an error
message with a retry option should appear in place of the loading indicator.
