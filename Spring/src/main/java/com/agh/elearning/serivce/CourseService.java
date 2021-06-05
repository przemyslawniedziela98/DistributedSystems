package com.agh.elearning.serivce;

import com.agh.elearning.data.Course;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final List<Course> courses = new ArrayList<>();

    public CourseService() {
        courses.add(new Course("1", "Java", "Super fancy course"));
        courses.add(new Course("2", "Scala", "Super fancy course in SCALA!!"));
        courses.add(new Course("3", "Matlab", "Course (not fancy)"));
    }

    public List<Course> getAllCourses() {
        return courses;
    }

    public Course getCourse(String id) {
        return courses.stream()
                .filter(course -> id.equals(course.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Course addCourse(Course course) {
        List<String> ids = courses.stream().map(Course::getId).collect(Collectors.toList());
        if (ids.contains(course.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        courses.add(course);
        return course;
    }

    public Course editCourse(String id, Course course) {
        Course courseCurrent = getCourse(id);
        if (!Objects.equals(courseCurrent.getName(), course.getName())) {
            courseCurrent.setName(course.getName());
        }
        if (!Objects.equals(courseCurrent.getDescription(), course.getDescription())) {
            courseCurrent.setDescription(course.getDescription());
        }
        return courseCurrent;
    }
    public Course deleteCourse(String id) {
        Course courseCurrent = getCourse(id);
        courses.remove(courseCurrent);
        return courseCurrent;
    }
}
