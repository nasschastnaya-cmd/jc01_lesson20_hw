package com.edu.educational_system.service;

import java.util.List;

import com.edu.educational_system.erepository.CourseRepository;
import com.edu.educational_system.model.Course;
import com.edu.educational_system.model.Person;

public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void createCourse(Course course) {
        courseRepository.saveCourse(course);
    }

    public boolean enrollPerson(Course course, Person person) {
        return course.addParticipant(person);
    }

    public void conductLesson(Course course) {
        course.conductLesson();
    }

    public List<Person> getParticipants(Course course) {
        return course.getParticipants();
    }

    public double getAverageGrade(Course course) {
        return course.calculateAverageGrade();
    }

    public List<Course> getAllCourses() {
        return courseRepository.getAllCourses();
    }
}
