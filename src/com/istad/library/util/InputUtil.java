package com.istad.library.util;

import java.math.BigDecimal;
import java.util.Scanner;

public class InputUtil {

    private static final Scanner input = new Scanner(System.in);

    public static String getText(String label) {
        TableUtil.print(label + "-> ", false);
        return input.nextLine();
    }

    public static Integer getInteger(String label) {
        do {
            TableUtil.print(label + "-> ", false);
            try {
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                TableUtil.print(e.getMessage(), true);
            }
        } while(true);
    }

}
