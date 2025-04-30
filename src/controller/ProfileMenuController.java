package controller;

import model.enums.ProfileMenuCommand;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenuController {
    private static final ProfileMenuController instance = new ProfileMenuController();

    public static ProfileMenuController getInstance() {
        return instance;
    }

    public void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            Matcher matcher;

            if ((matcher = ProfileMenuCommand.CHANGE_USERNAME.getMatcher(input)) != null) {
                changeUsername(matcher.group("username"));
            }
            else if ((matcher = ProfileMenuCommand.CHANGE_NICKNAME.getMatcher(input)) != null) {
                changeNickname(matcher.group("nickname"));
            }
            else if ((matcher = ProfileMenuCommand.CHANGE_EMAIL.getMatcher(input)) != null) {
                changeEmail(matcher.group("email"));
            }
            else if ((matcher = ProfileMenuCommand.CHANGE_PASSWORD.getMatcher(input)) != null) {
                changePassword(matcher.group("oldPassword"), matcher.group("newPassword"));
            }
            else if ((matcher = ProfileMenuCommand.SHOW_USER_INFO.getMatcher(input)) != null) {
                showUserInfo();
            }
            else {
                System.out.println("Invalid command!");
            }
        }
    }

    private void changeUsername(String newUsername) {
        // بررسی برابر نبودن با نام کاربری فعلی
        // بررسی معتبر بودن نام کاربری
        // تغییر نام کاربری در صورت امکان
    }

    private void changeNickname(String newNickname) {
        // بررسی برابر نبودن با نام مستعار فعلی
        // تغییر نام مستعار
    }

    private void changeEmail(String newEmail) {
        // بررسی معتبر بودن ایمیل
        // بررسی برابر نبودن با ایمیل فعلی
        // تغییر ایمیل
    }

    private void changePassword(String oldPassword, String newPassword) {
        // بررسی صحت گذرواژه قدیمی
        // بررسی عدم تطابق گذرواژه جدید با قدیمی
        // تغییر گذرواژه
    }

    private void showUserInfo() {
        // نمایش اطلاعات کاربر شامل:
        // - نام کاربری
        // - نام مستعار
        // - ایمیل
        // - تاریخ عضویت
        // - جنسیت (که قابل تغییر نیست)
    }
}