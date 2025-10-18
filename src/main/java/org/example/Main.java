package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Gson gson = new Gson();
        List<User> users;

        try (Reader reader = new FileReader("books.json")) {
            users = gson.fromJson(reader, new TypeToken<List<User>>() {}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Задание 1: Вывести список посетителей и их количество
        System.out.println("Task 1: List of visitors and their count");
        System.out.println("Total number of visitors: " + users.size());
        users.forEach(System.out::println);

        //Задание 2: Вывести список и количество всех книг, добавленных посетителями в избранное, без повторений
        System.out.println("\nTask 2: Unique books in favorites");
        Set<Book> books = users.stream()
                .flatMap(user -> user.getFavoriteBooks().stream())
                .collect(Collectors.toSet());
        int booksCount = books.size();
        System.out.println("Number of unique books: " + booksCount);
        books.forEach(System.out::println);

        //Задание 3: Отсортировать и вывести список всех книг по году издания
        System.out.println("\nTask 3: Books sorted by publication year");
        List<Book> sortedBooks = users.stream()
                .flatMap(user -> user.getFavoriteBooks().stream())
                .sorted(Comparator.comparingInt(Book::getPublishingYear))
                .collect(Collectors.toList());
        sortedBooks.forEach(book -> System.out.println(book.getName() + " (" + book.getPublishingYear() + ")"));

        //Задание 4: Проверить, есть ли у кого-то из посетителей в списке избранных книг произведение автора "Jane Austen"
        System.out.println("\nTask 4: Search for Jane Austen works");
        boolean hasJaneAusten = users.stream()
                .flatMap(user -> user.getFavoriteBooks().stream())
                .anyMatch(book -> book.getAuthor().equals("Jane Austen"));
        System.out.println("Are there any Jane Austen books: " + hasJaneAusten);

        List<User> usersLikedJane = users.stream()
                .filter(user -> user.getFavoriteBooks().stream()
                        .anyMatch(book -> book.getAuthor().equals("Jane Austen")))
                .collect(Collectors.toList());
        if (!usersLikedJane.isEmpty()) {
            System.out.println("Visitors with Jane Austen books:");
            usersLikedJane.forEach(user -> {
                System.out.print(user.getName() + " " + user.getSurname() + ": ");
                user.getFavoriteBooks().stream()
                        .filter(book -> book.getAuthor().equals("Jane Austen"))
                        .forEach(book -> System.out.print(book.getName() + " "));
                System.out.println();
            });
        }

        //Задание 5: Вывести максимальное число книг, добавленных одним посетителем в избранное
        System.out.println("\nTask 5: Maximum number of books per visitor");
        OptionalInt maxBooks = users.stream()
                .mapToInt(user -> user.getFavoriteBooks().size())
                .max();
        if (maxBooks.isPresent()) {
            System.out.println("Maximum number of books: " + maxBooks.getAsInt());
            // Покажем кто это
            users.stream()
                    .filter(user -> user.getFavoriteBooks().size() == maxBooks.getAsInt())
                    .forEach(user -> System.out.println("Visitor: " + user.getName() + " " + user.getSurname()));
        }

        //Задание 6: Создать SMS-сообщения для подписанных пользователей
        System.out.println("\nTask 6: SMS messages for subscribed users");
        double averageBooks = users.stream()
                .mapToInt(user -> user.getFavoriteBooks().size())
                .average()
                .orElse(0.0);
        System.out.printf("Average number of books: %.2f%n", averageBooks);

        List<SmsMessage> smsMessages = users.stream()
                .filter(User::getSubscribed)
                .map(user -> {
                    int bookCount = user.getFavoriteBooks().size();
                    String message;
                    if (bookCount > averageBooks) {
                        message = "you are a bookworm";
                    } else if (bookCount < averageBooks) {
                        message = "read more";
                    } else {
                        message = "fine";
                    }
                    return new SmsMessage(user.getPhone(), message);
                })
                .collect(Collectors.toList());
        System.out.println("Generated SMS messages:");
        smsMessages.forEach(System.out::println);

        // Статистика по сообщениям
        Map<String, Long> messageStats = smsMessages.stream()
                .collect(Collectors.groupingBy(SmsMessage::getMessage, Collectors.counting()));
        System.out.println("SMS Statistics:");
        messageStats.forEach((message, count) ->
                System.out.println("'" + message + "': " + count + " people"));
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    private String name;
    private String surname;
    private String phone;
    private Boolean subscribed;
    private List<Book> favoriteBooks = new ArrayList<>();

    @Override
    public String toString() {
        return name + " " + surname + " | " + phone + " | " +
                (subscribed ? "Subscribed" : "Not subscribed") + " | Books: " +
                (favoriteBooks != null ? favoriteBooks.size() : 0);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Book {
    private String name;
    private String author;
    private Integer publishingYear;
    private String isbn;
    private String publisher;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "'" + name + "' - " + author + " (" + publishingYear + ")";
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class SmsMessage {
    private String phoneNumber;
    private String message;

    @Override
    public String toString() {
        return "SMS to " + phoneNumber + ": '" + message + "'";
    }
}
