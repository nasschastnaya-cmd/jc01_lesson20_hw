package com.edu.educational_system.ui;

import java.util.List;
import java.util.Scanner;

import com.edu.educational_system.controller.CourseController;
import com.edu.educational_system.model.Administrator;
import com.edu.educational_system.model.Course;
import com.edu.educational_system.model.Person;
import com.edu.educational_system.model.Student;
import com.edu.educational_system.model.Teacher;

public class CourseConsoleView {
    private final Scanner scanner = new Scanner(System.in);
    private final CourseController controller;
    private Course currentCourse;

    public CourseConsoleView(CourseController controller) {
        this.controller = controller;
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n==== Course Management Menu ====");
            System.out.println("1. Create Course");
            System.out.println("2. Add Participant");
            System.out.println("3. Start Lesson");
            System.out.println("4. Show Course Info");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> createCourse();
                case 2 -> addParticipant();
                case 3 -> startLesson();
                case 4 -> showCourseInfo();
                case 5 -> {
                    System.out.println("Exiting.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void createCourse() {
        System.out.print("Course name: ");
        String courseName = scanner.nextLine();
        currentCourse = new Course(courseName);
        controller.createCourse(currentCourse);
        System.out.println("Course created.");
    }

    private void addParticipant() {
        if (currentCourse == null) {
            System.out.println("Please create a course first.");
            return;
        }

        System.out.println("Select role: 1 - Student, 2 - Teacher, 3 - Administrator");
        int role = Integer.parseInt(scanner.nextLine());

        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Person person = null;

        switch (role) {
            case 1 -> {
                System.out.print("Group: ");
                String group = scanner.nextLine();
                System.out.print("Average grade: ");
                double grade = Double.parseDouble(scanner.nextLine());
                person = new Student(name, email, group, grade);
            }
            case 2 -> {
                System.out.print("Subject: ");
                String subject = scanner.nextLine();
                person = new Teacher(name, email, subject);
            }
            case 3 -> {
                System.out.print("Department: ");
                String dept = scanner.nextLine();
                person = new Administrator(name, email, dept);
            }
            default -> System.out.println("Invalid role.");
        }

        if (person != null) {
            boolean added = controller.registerPerson(currentCourse, person);
            System.out.println(added ? "Participant added." : "Participant already exists.");
        }
    }

    private void startLesson() {
        if (currentCourse == null) {
            System.out.println("Please create a course first.");
            return;
        }
        controller.startLesson(currentCourse);
    }

    private void showCourseInfo() {
        if (currentCourse == null) {
            System.out.println("No course available.");
            return;
        }

        System.out.println("\nCourse: " + currentCourse.getName());
        List<Person> participants = controller.getParticipants(currentCourse);
        for (Person p : participants) {
            System.out.println("- " + p.getName() + " | " + p.getRoleDescription());
        }
        System.out.printf("Average Student Grade: %.2f\n", controller.getAverageGrade(currentCourse));
    }
}
