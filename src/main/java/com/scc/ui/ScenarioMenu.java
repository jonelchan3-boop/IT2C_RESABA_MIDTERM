package com.scc.ui;

import com.scc.dao.ScenarioDao;
import com.scc.model.ScenarioEntity;
import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ScenarioDao dao = new ScenarioDao();

    public void displayMenu() {
        while (true) {
            printHeader("CLINIC PATIENT APPOINTMENT REGISTRY");
            System.out.println("[1] Add New Appointment");
            System.out.println("[2] View Scheduled Appointments");
            System.out.println("[3] Exit System");
            printSeparator();
            System.out.print("Enter choice: ");

            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("⚠️ Enter a number only!\n");
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // clear leftover newline

            switch (choice) {
                case 1 -> addAppointmentFlow();
                case 2 -> viewAppointmentsFlow(); // ✅ YOUR VIEW HERE
                case 3 -> {
                    System.out.println("Exiting system... Thank you!");
                    return;
                }
                default -> System.out.println("⚠️ Invalid choice. Try 1, 2, or 3.\n");
            }
        }
    }

    private void addAppointmentFlow() {
        printHeader("BOOK APPOINTMENT");

        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine().trim();
        if (name.isBlank()) {
            System.out.println("⚠️ Name cannot be empty!\n");
            return;
        }

        System.out.print("Enter Doctor Assigned: ");
        String doctor = scanner.nextLine().trim();
        if (doctor.isBlank()) {
            System.out.println("⚠️ Doctor name cannot be empty!\n");
            return;
        }

        System.out.print("Enter Consultation Fee: ");
        if (!scanner.hasNextDouble()) {
            scanner.nextLine();
            System.out.println("⚠️ Enter a valid number!\n");
            return;
        }
        double fee = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Status (SCHEDULED / COMPLETED): ");
        String status = scanner.nextLine().trim().toUpperCase();
        if (!status.equals("SCHEDULED") && !status.equals("COMPLETED")) {
            System.out.println("⚠️ Only SCHEDULED or COMPLETED allowed!\n");
            return;
        }

        boolean ok = dao.addAppointment(name, doctor, fee, status);
        if (ok) {
            System.out.println("\n✅ SUCCESS: Appointment successfully booked!\n");
        } else {
            System.out.println("\n❌ Failed to save.\n");
        }
    }

    // =============================================
    // === CHOICE 2 — THIS SHOWS YOUR APPOINTMENTS
    // =============================================
    private void viewAppointmentsFlow() {
        printHeader("CLINIC APPOINTMENT SCHEDULE");

        List<ScenarioEntity> list = dao.getAllAppointments();

        System.out.println("+-----+----------------------+----------------------+-------------+------------+");
        System.out.printf("| %-3s | %-20s | %-20s | %-11s | %-10s |%n",
                "ID", "PATIENT NAME", "DOCTOR", "CONSULT FEE", "STATUS");
        System.out.println("+=====+======================+======================+=============+============+");

        if (list.isEmpty()) {
            System.out.printf("| %-66s |%n", " No appointments found. Add one first!");
        } else {
            for (ScenarioEntity a : list) {
                System.out.printf("| %-3d | %-20s | %-20s | %,11.2f | %-10s |%n",
                        a.id(),
                        a.patientName(),
                        a.doctor(),
                        a.consultFee(),
                        a.status());
            }
        }

        System.out.println("+-----+----------------------+----------------------+-------------+------------+");
        System.out.printf("Total Records Displayed: %d%n%n", list.size());
    }

    private void printHeader(String title) {
        printSeparator();
        int pad = (54 - title.length()) / 2;
        System.out.println(" ".repeat(Math.max(0, pad)) + title);
        printSeparator();
    }

    private void printSeparator() {
        System.out.println("-".repeat(54));
    }
}