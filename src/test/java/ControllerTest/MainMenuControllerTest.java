package ControllerTest;

import controller.MainMenuController;
import model.App;
import model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MainMenuControllerTest {
    private MainMenuController controller;

    @BeforeEach
    void setUp() {

        controller = new MainMenuController();
    }

    @Test
    void testInvalidCommand() {
        String input= "Hi";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Invalid command!", result.Message());
    }

    @Test
    void testInvalidMenuName() {
        String input= "change menu to InvalidMenu";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Invalid menu name.", result.Message());
    }

    @Test
    void testSuccessfulLogout() {
        String input= "user logout";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Logged out successfully.", result.Message());
    }

    @Test
    void testSuccessfulShowMenu() {
        String input= "show  current menu ";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("You're now in " + App.getCurrentMenu().getName() + ".", result.Message());
    }

    @Test
    void testSuccessfulChangeToProfile() {
        String input= "change menu to profile";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("You are now in profile menu.", result.Message());
    }

    @Test
    void testSuccessfulChangeToGame() {
        String input= "change menu to game";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("You are now in game menu.", result.Message());
    }

}
