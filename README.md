The controller in this project is responsible for processing user input and connects game objects (player, rooms, objects) with command logic. Highlights:

- **The main cycle:**
  The 'runGameLoop()' method constantly requests commands from the user until the `running` flag becomes false.

- **Command processing:**
  The 'handleInput(String input)` method splits the input into commands and arguments, using the `switch-case` construct to call the corresponding methods (`lookAround()`, `move()`, `pickUp()`, `checkInventory()`, `ShowHelp()`).

- **Functional commands:**
- `look': Displays a description of the current room, objects on the ground.
- `move': Moves the player if there is an adjacent room in the specified direction.
- `pick up': Removes an item from a room and adds it to the player's inventory.
- `inventory`: Displays a list of items in the inventory.
- `help`: Displays a list of available commands.
- **Error handling:**
  The controller outputs understandable messages in the absence of a direction, an incorrect command format, or the absence of the desired object.

Thus, the controller provides a simple but flexible mechanism for player interaction with the game world, easily expanded by additional functions.