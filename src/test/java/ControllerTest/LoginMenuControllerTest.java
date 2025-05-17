package ControllerTest;

import controller.LoginMenuController;
import model.App;
import model.Hasher;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import model.Result;
import static org.junit.jupiter.api.Assertions.*;

public class LoginMenuControllerTest {

    private LoginMenuController controller;

    @BeforeEach
    void setUp() {
        controller = new LoginMenuController();
        App.getRegisteredPlayers().clear();

        Player player = new Player(
                "test",
                Hasher.Hash("Valid123!"),
                "test",
                "test@exa.mple.com",
                true,
                "What is your favorite toy's name?",
                "Teddy"
        );
        App.addRegisteredPlayer(player);
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
    void testUsernameIsValid() { // registration is canceled after invalid username detected
    String input = "register -u iliya.-maleky -p Pass123Val!d Pass123Val!d -n Iliya -e Iliya@gmail.com -g male \n   stop    ";
    Scanner scanner = new Scanner(input);
    Result result = controller.run(scanner);
    assertFalse(result.Success());
    assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testUsernameLength1() {// this test checks when username is 16 letters long
        String input = "register -u ArazSoleymanpour -p Pass123Val!d Pass123Val!d -n Iliya -e Iliya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.",
                result.Message());
    }

    @Test
    void testUsernameLength2() {// this test checks when username is 15 letters long
        String input = "register -u ArazSoleymanpur -p Pass123Val!d Pass123Val!d -n Iliya -e Iliya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.",
                result.Message());
    }

    @Test
    void testUsernameLength3() {// this test checks when username is 3 letters long
        String input = "register -u ili -p Pass123Val!d Pass123Val!d -n Iliya -e Iliya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.",
                result.Message());
    }

    @Test
    void testUsernameLength4() {// this test checks when username is 4 letters long
        String input = "register -u araz -p Pass123Val!d Pass123Val!d -n Iliya -e Iliya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.",
                result.Message());
    }

    @Test
    void testUsernameChars() {// this test checks whether illegal characters have been used
        String input = "register -u il-i@mal.eky -p Pass123Val!d Pass123Val!d -n Iliya -e Iliya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.",
                result.Message());
    }

    @Test
    void testUsernameNull() {// this test checks if there even is a username
        String input = "register -u  -p Pass123Val!d Pass123Val!d -n Iliya -e Iliya@gmail.com -g male  ";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Invalid command!", result.Message());
    }

    @Test
    void testEmailHasAtSign1() {// this test checks when email has no @
        String input = "register -u iliya -p Pass123Val!d Pass123Val!d -n Iliya -e Iliyagmail.com -g male \nstop ";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testEmailHasAtSign2() {// this test checks when email has more than one @
        String input = "register -u iliya -p Pass123Val!d Pass123Val!d -n Iliya -e Iliya@@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testEmailHasIllegalChars1() {
        String input = "register -u iliya -p Pass123Val!d Pass123Val!d -n Iliya -e Iliya@gm/ail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testEmailHasIllegalChars2() {
        String input = "register -u iliya -p Pass123Val!d Pass123Val!d -n Iliya -e Il\\iya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testEmailTLD1() {
        String input = "register -u iliya -p Pass123Val!d Pass123Val!d -n Iliya -e Iliya@gmailcom -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testEmailTLD2() {
        String input = "register -u iliya -p Pass123Val!d Pass123Val!d -n Iliya -e Iliya@gmail. -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testEmailUserInitChar() {
        String input = "register -u iliya -p Pass123Val!d Pass123Val!d -n Iliya -e -liya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testEmailUserIllegalChar() {
        String input = "register -u iliya -p Pass123Val!d Pass123Val!d -n Iliya -e Il%iya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testEmailUserConsecutiveDots() {
        String input = "register -u iliya -p Pass123Val!d Pass123Val!d -n Iliya -e Il..iya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void TestEmailDomainDots() {
        String input = "register -u iliya -p Pass123Val!d Pass123Val!d -n Iliya -e Iliya@gmailcom -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testPassLength() {
        String input = "register -u iliya -p Pass Pass -n Iliya -e Iliya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testPassStrength1() { // missing special symbol
        String input = "register -u iliya -p Pass123123 Pass123123 -n Iliya -e Iliya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testPassStrength2() { // missing Capital letter
        String input = "register -u iliya -p pass123123! pass123123! -n Iliya -e Iliya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testPassStrength3() { // missing lower-case letter
        String input = "register -u iliya -p PASS123123! PASS123123! -n Iliya -e Iliya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testPassStrength4() { // missing number
        String input = "register -u iliya -p Pass!@#!@# Pass!@#!@# -n Iliya -e Iliya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testPassConfirmation() {
        String input = "register -u iliya -p Password123! Passwrd123! -n Iliya -e Iliya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testNicknameLength1() { //checks if nickname shorter than 3 letters
        String input = "register -u iliya -p Password123! Password123! -n Il -e Iliya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testNicknameLength2() { //checks if nickname longer than 15 letters
        String input = "register -u iliya -p Password123! Password123! -n IliyaIliyaIliyaI -e Iliya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testNicknameIllegalChars() {
        String input = "register -u iliya -p Password123! Password123! -n Iliya12 -e Iliya@gmail.com -g male  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testGender() {
        String input = "register -u iliya -p Password123! Password123! -n Ili -e Iliya@gmail.com -g stainlessstell  \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Registration unsuccessful.", result.Message());
    }

    @Test
    void testCorrectInput() {
        String input = "register -u iliya -p Password123! Password123! -n Ili -e Iliya@gm.ail.com -g male \n1\nTeddy";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertTrue(result.Success());
        assertEquals("Successfully registered user!", result.Message());
    }

    @Test
    void testRecoveryInvalidUsername() {
        String input = "forget password -u Invalid";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Username doesn't exist.", result.Message());
    }

    @Test
    void testRecoveryInvalidAnswer() {
        String input = "forget password -u test \nbobby";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Incorrect answer.", result.Message());
    }

    @Test
    void testRecoveryPassMismatch() {
        String input = "forget password -u test \nteddy \nStrongPas123! StrongPass123! \nstop";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertFalse(result.Success());
        assertEquals("Recovery unsuccessful.", result.Message());
    }

    @Test
    void SuccessfulRecovery() {
        String input = "forget password -u test \nteddy \nStrongPass123! StrongPass123!";
        Scanner scanner = new Scanner(input);
        Result result = controller.run(scanner);
        assertTrue(result.Success());
        assertTrue(result.Message().contains("Your new password is: "));
    }


}
