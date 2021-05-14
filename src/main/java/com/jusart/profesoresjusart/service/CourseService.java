package com.jusart.profesoresjusart.service;

import java.util.List;

import com.jusart.profesoresjusart.model.Course;

public interface CourseService {

	void saveCourse(Course course);
	
	void deleteCourse(Long idCourse);
	
	void updateCourse(Course idCourse);
	
	List<Course> findAllCourse();
	
	Course findById(Long idCourse);
	
	Course findByName(String name);
	
	List<Course> findByIdTeacher(Long idTeacher);
}
