package com.istad.library.util;

import java.util.List;
import java.util.Scanner;

public class PaginationUtil {

    public static <T> void paginateList(List<T> items, int pageSize) {
        if (items == null || items.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        int currentPage = 0;
        int totalPages = (int) Math.ceil((double) items.size() / pageSize);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, items.size());

            System.out.println("\n--- Page " + (currentPage + 1) + " of " + totalPages + " ---");
            for (int i = start; i < end; i++) {
                System.out.println((i + 1) + ". " + items.get(i).toString());
            }

            System.out.println("\n[N]ext | [P]rev | [E]xit");
            System.out.print(">>> ");
            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "N":
                    if (currentPage < totalPages - 1) currentPage++;
                    else System.out.println("You are at the last page.");
                    break;

                case "P":
                    if (currentPage > 0) currentPage--;
                    else System.out.println("You are at the first page.");
                    break;

                case "E":
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
