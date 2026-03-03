package com.edu.educational_system.erepository; 

import java.io.*;
import java.util.*;
import com.edu.educational_system.model.Person;
import com.edu.educational_system.model.Course;
import com.edu.educational_system.model.Student;
import com.edu.educational_system.model.Teacher;

public class CourseRepository {
    private final String FILE_NAME = "courses.txt"; 

public void saveCourse(Course course) {
    try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME, true))) {
        out.print(course.getName());
        
        for (Person p : course.getParticipants()) {
            // Добавляем роль в конец: Имя;Почта;Роль
            // p.getClass().getSimpleName() автоматически вернет "Student" или "Teacher"
            String role = p.getClass().getSimpleName();
            out.print("," + p.getName() + ";" + p.getEmail() + ";" + role);
        }
        out.println(); 
    } catch (IOException e) {
        System.err.println("Ошибка записи: " + e.getMessage());
    }
}

// ЧТЕНИЕ
public List<Course> getAllCourses() {
    List<Course> courses = new ArrayList<>();
    File file = new File(FILE_NAME);

    if (!file.exists()) return courses;

    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            
            if (parts.length > 0) {
                Course course = new Course(parts[0]);
                
                for (int i = 1; i < parts.length; i++) {
                    String[] pDetails = parts[i].split(";");
                    
                    if (pDetails.length >= 3) { // Теперь проверяем на 3 элемента
                        String name = pDetails[0];
                        String email = pDetails[1];
                        String role = pDetails[2];

                        Person p;
                        // Проверяем роль и создаем нужный объект
                        if ("Student".equalsIgnoreCase(role)) {
                            p = new Student(name, email);
                        } else if ("Teacher".equalsIgnoreCase(role)) {
                            p = new Teacher(name, email);
                        } else {
                            continue; // Пропускаем, если роль неизвестна
                        }
                        
                        course.addParticipant(p);
                    }
                }
                courses.add(course);
            }
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    return courses;
}
}
