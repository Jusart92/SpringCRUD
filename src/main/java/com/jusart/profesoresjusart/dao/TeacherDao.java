package com.jusart.profesoresjusart.dao;

import java.util.List;

import com.jusart.profesoresjusart.model.Teacher;



public interface TeacherDao {

	void saveTeacher(Teacher teacher);

	void deleteTeacherById(Long idTeacher);

	void updateTeacher(Teacher teacher);

	List<Teacher> findAllTeachers();

	Teacher findById(Long idTeacher);

	Teacher findByName(String name);

}
