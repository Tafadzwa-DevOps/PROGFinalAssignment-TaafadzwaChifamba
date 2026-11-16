/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registration;


/**
 *
 * @author tefy
 */


/*
 * STUDENT DEBUGGING EXERCISE
 * Fix the errors in this file. Some errors prevent compilation; others cause
 * incorrect behaviour at runtime. Do not simply delete methods.
 */
package com.mycompany.chatapp;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class MessageDebuggingExercise {

    private static final int MAX_LIMIT = 100;
    private static String[] sentMessagesArray = new String[MAX_LIMIT];
    private static String[] storedMessagesArray = new String[MAX_LIMIT];
    private static String[] messageHashArray = new String[MAX_LIMIT];
    private static String[] messageIdArray = new String[MAX_LIMIT];
    private static String[] recipientArray = new String[MAX_LIMIT];

    private static int sentCount = 0;
    private static int storedCount = 0;
    private static int totalMessagesSent = 0;

    public static boolean checkMessageID(String messageId) {
        if (messageId == null) return false;
        return messageId.length() <= 10;
    }

    public static String checkRecipientCell(String cellNumber) {
        if (cellNumber != null && cellNumber.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted. Please try again.";
    }

    public static String checkMessageLength(String messageText) {
        if (messageText == null) return "Message is empty.";
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        int overrun = messageText.length() - 250;
        return "Message exceeds 250 characters by " + overrun + ".";
    }

    public static String createMessageHash(String messageId, int currentMsgNum, String messageText) {
        if (messageId == null || messageId.length() < 2 || messageText == null || messageText.trim().isEmpty()) {
            return "INVALID_HASH";
        }
        String firstTwoId = messageId.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words;
        String lastWord = words[words.length - 1];
        return (firstTwoId + ":" + currentMsgNum + ":" + firstWord + lastWord).toUpperCase();
    }

    public static String sendOrStoreMessage(Scanner input, String messageId,
                                            String recipient, String messageText) {
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.print("Choose an option: ");

        int choice = input.nextInt();
        input.nextLine();

        String hash = createMessageHash(messageId, totalMessagesSent + 1, messageText);
        int trackingIndex = sentCount + storedCount;

        if (choice == 1) {
            sentMessagesArray[sentCount] = messageText;
            messageHashArray[trackingIndex] = hash;
            messageIdArray[trackingIndex] = messageId;
            recipientArray[trackingIndex] = recipient;
            sentCount++;
            totalMessagesSent++;

            System.out.println("Message successfully sent.");
            return "Message successfully sent.";
        } else if (choice == 2) {
            System.out.println("Message disregarded.");
            return "Message disregarded.";
        } else if (choice == 3) {
            storedMessagesArray[storedCount] = messageText;
            messageIdArray[trackingIndex] = messageId;
            messageHashArray[trackingIndex] = hash;
            recipientArray[trackingIndex] = recipient;
            storedCount++;

            saveMessage(messageId, hash, recipient, messageText);
            return "Message successfully stored.";
        }

        return "Invalid choice.";
    }

    public static String getLongestStoredMessageText() {
        if (storedCount == 0) return "No messages stored.";

        String longest = storedMessagesArray;
        for (int i = 1; i < storedCount; i++) {
            if (storedMessagesArray[i] != null && storedMessagesArray[i].length() > longest.length()) {
                longest = storedMessagesArray[i];
            }
        }
        return longest;
    }

    private static void saveMessage(String id, String hash, String recipient, String text) {
        try (FileWriter writer = new FileWriter("stored_messages.json", true)) {
            writer.write(String.format("{\"id\":\"%s\",\"hash\":\"%s\",\"recipient\":\"%s\",\"message\":\"%s\"}%n",
                    id, hash, recipient, text));
        } catch (IOException e) {
            System.out.println("Error saving message: " + e.getMessage());
        }
    }
}
 
    
