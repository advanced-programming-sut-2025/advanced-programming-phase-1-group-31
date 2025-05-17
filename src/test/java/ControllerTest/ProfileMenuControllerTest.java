package ControllerTest;

import controller.ProfileMenuController;
import model.App;
import model.Player;
import model.Result;
import model.enums.general.Menus;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileMenuControllerTest {

    private ProfileMenuController controller;
    private List<Player> registeredPlayers;

    @BeforeEach
    void setup() {
        controller = new ProfileMenuController();
        Player testPlayer = new Player("Iliya", "Password123!", "ili", "Iliya@mal.eky.com", false, " ", " ");
        testPlayer.setCurrentMenu(Menus.ProfileMenu);
        testPlayer.setHighScore(200);
        testPlayer.setGameCount(5);
        App.setPlayerLoggedIn(testPlayer);

        registeredPlayers = new ArrayList<>();
        registeredPlayers.add(testPlayer);
    }

    @Test
    void testInvalidCommand() {
        Scanner scanner = new Scanner("Hi");
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Invalid command!", result.Message());
    }

    @Test
    void testShowInfo() {
        Scanner scanner = new Scanner("user info");
        Result result = controller.run(scanner);
        assertTrue(result.Success());
        assertTrue(result.Message().contains("Username: Iliya"));
    }

    @Test
    void testChangeUsernameSuccess() {
        Scanner scanner = new Scanner("change username -u Iliya123");
        Result result = controller.run(scanner);
        assertTrue(result.Success());
        assertEquals("Successfully changed username to Iliya123", result.Message());
        assertEquals("Iliya123", App.getPlayerLoggedIn().getUsername());
    }

    @Test
    void testChangeUsernameSameAsBefore() {
        Scanner scanner = new Scanner("change username -u Iliya");
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Username same as before.", result.Message());
    }

    @Test
    void testChangeUsernameInvalid() {
        Scanner scanner = new Scanner("change username -u 123");
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Username is invalid.", result.Message());
    }

    @Test
    void testChangeNicknameSuccess() {
        Scanner scanner = new Scanner("change nickname -n NewNick");
        Result result = controller.run(scanner);
        assertTrue(result.Success());
        assertEquals("NewNick", App.getPlayerLoggedIn().getNickname());
    }

    @Test
    void testChangeNicknameSameAsBefore() {
        Scanner scanner = new Scanner("change nickname -n   ili");
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Nickname same as before.", result.Message());
    }

    @Test
    void testChangeNicknameInvalid() {
        Scanner scanner = new Scanner("change nickname -n 123");
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Nickname is invalid.", result.Message());
    }

    @Test
    void testChangeEmailSuccess() {
        Scanner scanner = new Scanner("change email -e new.email@exa.mple.com");
        Result result = controller.run(scanner);
        assertTrue(result.Success());
        assertEquals("new.email@exa.mple.com", App.getPlayerLoggedIn().getEmail());
    }

    @Test
    void testChangeEmailSameAsBefore() {
        Scanner scanner = new Scanner("change email -e Iliya@mal.eky.com");
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Email same as before.", result.Message());
    }

    @Test
    void testChangeEmailInvalid() {
        Scanner scanner = new Scanner("change email -e Invalid@@example");
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Email is invalid.", result.Message());
    }

    @Test
    void testChangePasswordSuccess() {
        Scanner scanner = new Scanner("change password -p NewPass123! -o Password123!");
        Result result = controller.run(scanner);
        assertTrue(result.Success());
        assertEquals("Successfully changed password to NewPass123!", result.Message());
        assertEquals("NewPass123!", App.getPlayerLoggedIn().getPassword());
    }

    @Test
    void testChangePasswordWrongOld() {
        Scanner scanner = new Scanner("change password -p NewPass1! -o NewPss123!");
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Former password incorrect.", result.Message());
    }

    @Test
    void testChangePasswordSameAsBefore() {
        Scanner scanner = new Scanner("change password -p Password123! -o Password123!");
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Password same as before.", result.Message());
    }

    @Test
    void testChangePasswordInvalid() {
        Scanner scanner = new Scanner("change password -p Pass -o Password123!");
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Password is invalid.", result.Message());
    }

    @Test
    void testLogout() {
        Scanner scanner = new Scanner("user  logout");
        Result result = controller.run(scanner);
        assertTrue(result.Success());
        assertEquals("You have logged out!", result.Message());
        assertEquals(Menus.LoginMenu, App.getCurrentMenu());
    }
}
