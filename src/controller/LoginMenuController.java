package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Map;
import java.util.regex.Matcher;
import model.Result;
import model.enums.Menus;
import model.enums.commands.LoginMenuCommands;
import model.Game;
import model.Player;
import model.App;
import model.BackupQuestions;

public class LoginMenuController {
    public Result run(Scanner scanner){
        String line = scanner.nextLine();
        Matcher matcher;

        if((matcher = LoginMenuCommands.REGISTER.getMatcher(line)).matches()){
            return register(matcher, scanner);
        } else if((matcher = LoginMenuCommands.LOGIN.getMatcher(line)).matches()){
            return login(matcher);
        } else if((matcher = LoginMenuCommands.SHOW_CURRENT_MENU.getMatcher(line)).matches()){
            return showCurrentMenu(matcher);
        } else if((matcher = LoginMenuCommands.PASSWORD_RECOVERY.getMatcher(line)).matches()){
            return recoverPassword(matcher, scanner);
        } else if((matcher = LoginMenuCommands.EXIT_MENU.getMatcher(line)).matches()){
            return exitMenu();
        } else{
            return new Result(false, "invalid command!");
        }
    }

    private Result exitMenu() {
        Game.getActivePlayer().setCurrentMenu(Menus.ExitMenu);
        return new Result(true, "exited the game.");
    }

    private Result showCurrentMenu(Matcher matcher) {
        return new Result(true, Game.getActivePlayer().getCurrentMenu().getName());
    }

    private Result register(Matcher matcher, Scanner scanner) {
        String username = matcher.group("username");
        boolean generateRandomPass = matcher.group("flag").equals("-random");
        String password = matcher.group("password");
        String passwordConfirmation = matcher.group("passwordConfirmation");
        String nickname = matcher.group("nickname");
        String email = matcher.group("email");
        String userEmail = email.substring(0, email.indexOf("@"));
        String domainEmail = email.substring(email.indexOf("@") + 1, email.lastIndexOf("."));
        String topLevel = email.substring(email.lastIndexOf(".") + 1);
        String gender = matcher.group("gender");

        while (!isUsernameValid(username)) {
            System.out.println("Please enter new username:");
            username = scanner.nextLine();
        }

        while (!isUsernameUnique(username)) {
            String newUSername;
            if((newUSername = generateUsernames(username, scanner)) != null){
                username = newUSername; 
            } else{
                username = scanner.nextLine();
                while (!isUsernameValid(username)) {
                    System.out.println("Please enter new username:");
                    username = scanner.nextLine();
                }
                continue;
            }
        }

        while (!isEmailValid(email, userEmail, domainEmail, topLevel)) {
            System.out.println("Please enter new email:");
            email = scanner.nextLine();
            userEmail = email.substring(0, email.indexOf("@"));
            domainEmail = email.substring(email.indexOf("@") + 1, email.lastIndexOf("."));
            topLevel = email.substring(email.lastIndexOf(".") + 1);
        }

        if(generateRandomPass){
            String newPassword;
            if((newPassword = generatePassword(passwordConfirmation, scanner)) != null){
                password = newPassword;
            }
        }

        while (!generateRandomPass && !isPasswordValid(password, passwordConfirmation)) {
            System.out.println("Please enter new pasword: (enter r for random password)");
            password = scanner.nextLine();
            if(password.equals("r")){
                String newPassword;
                if((newPassword = generatePassword(passwordConfirmation, scanner)) != null){
                    password = newPassword;
                } else{
                    password = scanner.nextLine();
                    while (!isPasswordValid(newPassword, passwordConfirmation)) {
                        System.out.println("Please enter new password");
                    }
                }
                break;
            }
        }
        
        if(nickname.length() > 15 || nickname.length() < 3){
            return new Result(false, "nickname must be 3 to 15 letters long");
        }

        if(!nickname.matches("^[A-Za-z]{3, 15}$")){
            return new Result(false, "nickname can only have letters");
        }
        
        if(!gender.matches("^(female||male)$")){
            return new Result(false, "only valid genders are male and female(and stainless steel but we didn't have the budget for a third one)");
        }

        BackupQuestions questions = new BackupQuestions();
        questions.print();
        String question = null;
        String answer = null;
        Map<String, String> backup = new HashMap<>();

        while(question == null){
            try{
                int choice = Integer.parseInt(scanner.nextLine().trim());
    
                if(choice == 1){
                    question = questions.Q1;
                } else if(choice == 2){
                    question = questions.Q2;
                } else if(choice == 3){
                    question = questions.Q3;
                } else{
                    System.out.println("Please choose from the options.");
                }
            } catch(NumberFormatException e){
                System.out.println("Please choose from the options.");
            }
        }

        while (answer == null) {
            answer = scanner.nextLine();
        }

        backup.put(question, answer);

        Player player = new Player(username, password, nickname, email, gender.equals("female"), backup);
        App.addRegisteredPlayer(player);
        return new Result(true, "successfully registered user!");
    }
    private Result login(Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        boolean stayLoggedIn = matcher.group("flag").trim().equals("-stay-logged-in");// figure this out

        for(Player player : App.getRegisteredPlayers()){
            if(player.getUsername().equals(username)){
                if(player.getPassword().equals(password)){
                    App.setPlayerLoggedIn(player);
                    return new Result(true, "Welcome back " + username);
                } else{
                    return new Result(false, "Incorrect password");
                }
            }
        }

        return new Result(false, "Username doesn't exist");
    }
    private Result recoverPassword(Matcher matcher, Scanner scanner) {
        String username = matcher.group("username");
        String answer = null;
        Player playerInQuestion = null;

        for(Player player : App.getRegisteredPlayers()){
            if(player.getUsername().equals(username)){
                playerInQuestion = player;
            }
        }

        if(playerInQuestion == null){
            return new Result(false, "Username doesn't exist.");
        } else{
            System.out.println(playerInQuestion.getBackup().entrySet());
        }

        while (answer == null) {
            answer = scanner.nextLine();    
        }
        
        if(playerInQuestion.getBackup().containsValue(answer)){
            return new Result(true, "Your password is: " + playerInQuestion.getPassword());
        } else{
            return new Result(false, "Incorrect answer");
        }
    }

    private boolean isUsernameValid(String username){
        if(!username.matches("^[A-Za-z0-9.-]+$")){
            return false;
        }
        return true;
    }

    private boolean isUsernameUnique(String username){
        if(App.getRegisteredUsernames().contains(username)){
            System.out.println("Username already exists.");
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email, String userEmail, String domainEmail, String topLevel){
        if(email.indexOf("@") == -1 || email.indexOf("@") != email.lastIndexOf("@")) {
            System.out.println("Email must have exactly one @.");
            return false;
        }

        if(!email.matches("")){//fill this in later
            System.out.println("Can't use illegal symbols.");
            return false;
        }

        if(!userEmail.matches("^(?!.*\\.\\.)[A-Za-z0-9][A-Za-z0-9._-]*[A-Za-z0-9]$")){// check this later
            System.out.println("email username must start " + 
            " with numbers or any letter, it must only inculde numbers, any letter or these symbols(._-)" + 
            " also, no consecutive dots");
            return false;
        }

        if(!domainEmail.matches("^(?=.*\\.)[A-Za-z0-9][A-Za-z0-9-]*[A-Za-z0-9]$")){
            System.out.println("email domain must only include letters and be 4 to 15 letters long");
            return false;
        }
        
        if(!topLevel.matches("^\\.[a-z]{2,}$")){
            System.out.println("TLD must be at least two letters long");
            return false;
        }

        return true;
    }

    private boolean isPasswordValid(String password, String passwordConfirmation){
        if(password.length() < 8){
            System.out.println("password must be at least 8 characters long");
            return false;
        }

        if(!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*?~])" + 
        "[A-Za-z0-9!@#$%^&*?~]{8,}$")){
            System.out.println("password must include capital letter" + 
            " lowercase letter, number and special symbol(!@#$%^&*?~)"); 
            return false;
        }

        if(!passwordConfirmation.equals(password)){
            System.out.println("the passwords entered don't match");
            return false;
        }

        return true;
    }

    private String generateUsernames(String username, Scanner scanner){
        ArrayList<String> inUse = App.getRegisteredUsernames();
        String[] options = new String[3];
        Random random = new Random();

        for(int i = 0; i < 3; i++){
            boolean addNum = random.nextBoolean();

            if(addNum){
                int number = random.nextInt(10000);
                String newUername = username + number;
                if(inUse.contains(newUername)){
                    i--;
                    continue;
                }
                options[i] = username + number;
            } else{
                int insertPosition = random.nextInt(username.length() - 2) + 1;
                String before = username.substring(0, insertPosition);
                String after = username.substring(insertPosition);
                String newUsername = before + "-" + after;
                if(inUse.contains(newUsername)){
                    i--;
                    continue;
                }
                options[i] = newUsername;
            }
        }
        System.out.println("1- " + options[0]);
        System.out.println("2- " + options[1]);
        System.out.println("3- " + options[2]);
        System.out.println("4- Generate more");
        System.out.println("5- Add a new username yourself");
        while(true){
            try{
                int choice = Integer.parseInt(scanner.nextLine().trim());
    
                if(choice == 1){
                    return options[0];
                } else if(choice == 2){
                    return options[1];
                } else if(choice == 3){
                    return options[2];
                } else if(choice == 4){
                    return generateUsernames(username, scanner);
                } else if(choice == 5){
                    return null;
                } else{
                    System.out.println("Please choose from the options.");
                }
            } catch(NumberFormatException e){
                System.out.println("please choose from the options.");
            }
        }
    }

    private String generatePassword(String password, Scanner scanner){
        Random random = new Random();
        final String uppercase = "abcdefghijklmnopqrstuvwxyz";
        final String lowercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String numbers = "0123456789";
        final String specials = "?><,\"';:\\/|][}{+=)(*&^%$#!";
        final String all = uppercase + lowercase + numbers + specials;

        String[] options = new String[3];

        for(int i = 0; i < 3; i++){
            int length = random.nextInt(13) + 8;// max 20 chars, min 8

            List<Character> newPassword = new ArrayList<>();
            newPassword.add(uppercase.charAt(random.nextInt(uppercase.length())));
            newPassword.add(lowercase.charAt(random.nextInt(lowercase.length())));
            newPassword.add(numbers.charAt(random.nextInt(numbers.length())));
            newPassword.add(specials.charAt(random.nextInt(specials.length())));

            for(int j = 4; j < length; j++){
                newPassword.add(all.charAt(random.nextInt(all.length())));
            }

            Collections.shuffle(newPassword);

            StringBuilder stringBuilder = new StringBuilder();
            for(char c : newPassword){
                stringBuilder.append(c);
            }

            options[i] = stringBuilder.toString();
        }

        System.out.println("1- " + options[0]);
        System.out.println("2- " + options[1]);
        System.out.println("3- " + options[2]);
        System.out.println("4- Generate more");
        System.out.println("5- Add a new password yourself");
        while(true){
            try{
                int choice = Integer.parseInt(scanner.nextLine().trim());
    
                if(choice == 1){
                    return options[0];
                } else if(choice == 2){
                    return options[1];
                } else if(choice == 3){
                    return options[2];
                } else if(choice == 4){
                    return generatePassword(password, scanner);
                } else if(choice == 5){
                    return null;
                } else{
                    System.out.println("Please choose from the options.");
                }
            } catch(NumberFormatException e){
                System.out.println("Please choose from the options.");
            }
        }
    }
}