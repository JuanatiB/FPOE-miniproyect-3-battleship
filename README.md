# Battleship Game 🎮⚓

Welcome to **Battleship Game**, a classic strategy game where a human player faces off against a computer opponent. This digital version of the game features an intuitive graphical interface, dynamic gameplay, and engaging AI mechanics, all implemented using Java and JavaFX.

## 🚀 Project Overview

Battleship is a turn-based strategy game where two players compete to sink each other's fleet of ships. In this digital adaptation, the human player challenges the computer, trying to guess the location of enemy ships hidden on a grid. Each player takes turns launching attacks until one fleet is completely destroyed.

## 🛠️ Features

- **Two Game Boards**:
    - **Player's Board**: Displays the human player's ships and incoming shots from the computer.
    - **Enemy's Board**: Where the player launches attacks to discover and sink the computer's fleet.
- **Fleet Setup**:
    - 1 Carrier (4 cells)
    - 2 Submarines (3 cells each)
    - 3 Destroyers (2 cells each)
    - 4 Patrol Boats (1 cell each)
- **Gameplay Mechanics**:
    - **Miss (Water)**: Marks an empty cell (displayed as "X").
    - **Hit (Damaged Ship)**: Partially hits a multi-cell ship.
    - **Sunk (Destroyed Ship)**: Completely destroys a ship.
- **Turn-Based Combat**: Players alternate turns; if a player hits, they get another shot.
- **AI Opponent**: Computer randomly places ships and fires shots.
- **Save and Resume Game**: Automatically saves the game state after each move, allowing players to pick up where they left off.

## 🎨 User Interface

- Developed using **JavaFX** for a responsive and visually appealing layout.
- Utilizes **Scene Builder** for designing the user interface.
- Drag-and-drop functionality for placing ships on the player's board.
- Real-time feedback on shots fired and ships hit.

## 🧩 Technical Highlights

- **Architecture**: Follows the **Model-View-Controller (MVC)** design pattern for clean and maintainable code.
- **Data Persistence**:
    - Serializable files to save the game state.
    - Plain text files for player data (e.g., nickname and number of ships sunk).
- **Event Handling**: Implements mouse events for user interactions.
- **Error Handling**: Robust use of Java exceptions, including custom exceptions.
- **Version Control**: Managed with **Git** and hosted on **GitHub**.

## 🖥️ Technologies Used

- **Java**: Core logic and game mechanics.
- **JavaFX**: GUI components and animations.
- **Scene Builder**: UI layout design.
- **IntelliJ IDEA**: Development environment.
- **Git & GitHub**: Version control and collaboration.

## 📦 Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/battleship-game.git
    cd battleship-game
    ```
2. **Open in IntelliJ IDEA**:
    - Import the project as a Maven project.
3. **Run the application**:
    - Execute `Main.java` from the `src` folder.

## 🎮 How to Play

1. **Set up your fleet**:
    - Drag and drop your ships onto your board.
    - Ships can be placed horizontally or vertically.
2. **Start the game**:
    - Take turns firing shots at the enemy's board.
    - A hit allows you to shoot again; a miss passes the turn to the computer.
3. **Victory Conditions**:
    - Sink all of the opponent's ships to win!

## 📝 Future Enhancements

- **Improved AI**: Smarter targeting algorithms for a more challenging opponent.
- **Multiplayer Mode**: Play against other human players over a network.
- **Customizable Boards**: Different board sizes and ship configurations.
- **Leaderboard**: Track high scores and game statistics.

## 🤝 Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

1. Fork the repository
2. Create a new branch (`git checkout -b feature-branch`)
3. Commit your changes (`git commit -m 'Add new feature'`)
4. Push to the branch (`git push origin feature-branch`)
5. Open a pull request

## 📜 License

This project is open-source and available under the **MIT License**.

