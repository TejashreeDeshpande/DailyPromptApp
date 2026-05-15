# Feature: Network Status Banner

The product team wants to add a network connectivity indicator to the app.

When the device loses internet connectivity, a banner or snackbar should appear informing
the user that they are offline. Any actions that require network access should be disabled
or show an appropriate message while offline. When connectivity is restored, the banner
should automatically dismiss and the disabled actions should become available again. The
connectivity state should be observed reactively — no manual refresh should be required.
