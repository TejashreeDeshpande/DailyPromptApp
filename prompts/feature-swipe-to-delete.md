# Feature: Swipe-to-Delete List

The product team wants to add swipe-to-delete to a list screen.

Users should see a list of items. Swiping a list item to the left should reveal a Delete
action. Confirming the delete should remove the item from the list with a brief animation.
An undo snackbar should appear after deletion, allowing the user to restore the item within
a few seconds. If the user does not tap undo before the snackbar dismisses, the deletion
should be permanent.
