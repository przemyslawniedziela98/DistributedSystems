package com.agh.elearning.controller;

import com.agh.elearning.data.Course;
import com.agh.elearning.serivce.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("courses")
public class CoursesController {

    private final CourseService courseService;

    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable String id) {
        return courseService.getCourse(id);
    }

    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @PutMapping("/{id}")
    public Course editCourse(@PathVariable String id, @RequestBody Course course) {
        return courseService.editCourse(id, course);
    }
    @DeleteMapping("/{id}")
    public Course deleteCourse(@PathVariable String id) {
        return courseService.deleteCourse(id);
    }
}
