package com.example.mud.controller;

import com.example.mud.player.Player;
import com.example.mud.world.Room;
import com.example.mud.world.Item;

import java.util.Scanner;


public class MUDController {
    private final Player player;
    private boolean running;

    public MUDController(Player player) {
        this.player = player;
        this.running = true;
    }

    public void runGameLoop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в MUD игру! Введите 'help' для списка команд.");
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            handleInput(input);
        }
        System.out.println("Игра завершена. До свидания!");
        scanner.close();
    }


    public void handleInput(String input) {
        if (input.isEmpty()) return;
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String arg = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case "look":
                lookAround();
                break;
            case "move":
                if (arg.isEmpty()) {
                    System.out.println("Укажите направление: forward, back, left, right.");
                } else {
                    move(arg.toLowerCase());
                }
                break;
            case "pick":
                // ожидается команда в виде "pick up <предмет>"
                if (arg.toLowerCase().startsWith("up ")) {
                    String itemName = arg.substring(3).trim();
                    pickUp(itemName);
                } else {
                    System.out.println("Неверная команда. Попробуйте: pick up <предмет>");
                }
                break;
            case "inventory":
                checkInventory();
                break;
            case "help":
                showHelp();
                break;
            case "quit":
            case "exit":
                running = false;
                break;
            default:
                System.out.println("Неизвестная команда.");
        }
    }


    private void lookAround() {
        Room currentRoom = player.getCurrentRoom();
        System.out.println("Вы находитесь в " + currentRoom.getName());
        System.out.println(currentRoom.getDescription());
        if (currentRoom.getItems().isEmpty()) {
            System.out.println("Здесь нет предметов.");
        } else {
            System.out.print("На земле лежат: ");
            for (Item item : currentRoom.getItems()) {
                System.out.print(item.getName() + " ");
            }
            System.out.println();
        }
    }

    private void move(String direction) {
        Room currentRoom = player.getCurrentRoom();
        Room nextRoom = currentRoom.getAdjacentRoom(direction);
        if (nextRoom == null) {
            System.out.println("Вы не можете идти в этом направлении!");
        } else {
            player.setCurrentRoom(nextRoom);
            System.out.println("Вы переместились в " + nextRoom.getName());
            System.out.println(nextRoom.getDescription());
        }
    }


    private void pickUp(String itemName) {
        Room currentRoom = player.getCurrentRoom();
        Item item = currentRoom.removeItem(itemName);
        if (item == null) {
            System.out.println("Предмет '" + itemName + "' не найден в этой комнате.");
        } else {
            player.addItem(item);
            System.out.println("Вы подобрали " + item.getName());
        }
    }


    private void checkInventory() {
        if (player.getInventory().isEmpty()) {
            System.out.println("Ваш инвентарь пуст.");
        } else {
            System.out.println("В вашем инвентаре:");
            for (Item item : player.getInventory()) {
                System.out.println("- " + item.getName());
            }
        }
    }


    private void showHelp() {
        System.out.println("Доступные команды:");
        System.out.println("look                     - осмотреть текущее помещение");
        System.out.println("move <направление>       - переместиться (forward, back, left, right)");
        System.out.println("pick up <предмет>        - подобрать предмет");
        System.out.println("inventory                - показать инвентарь");
        System.out.println("help                     - показать эту справку");
        System.out.println("quit или exit            - выйти из игры");
    }
}