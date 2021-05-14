package com.jusart.profesoresjusart.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jusart.profesoresjusart.dao.CourseDao;
import com.jusart.profesoresjusart.model.Course;

@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseDao _courseDao;

	@Override
	public void saveCourse(Course course) {
		// TODO Auto-generated method stub
		_courseDao.saveCourse(course);
	}

	@Override
	public void deleteCourse(Long idCourse) {
		// TODO Auto-generated method stub
		_courseDao.deleteCourse(idCourse);
	}

	@Override
	public void updateCourse(Course idCourse) {
		// TODO Auto-generated method stub
		_courseDao.updateCourse(idCourse);
	}

	@Override
	public List<Course> findAllCourse() {
		// TODO Auto-generated method stub
		return _courseDao.findAllCourse();
	}

	@Override
	public Course findById(Long idCourse) {
		// TODO Auto-generated method stub
		return _courseDao.findById(idCourse);
	}

	@Override
	public Course findByName(String name) {
		// TODO Auto-generated method stub
		return _courseDao.findByName(name);
	}

	@Override
	public List<Course> findByIdTeacher(Long idTeacher) {
		// TODO Auto-generated method stub
		return _courseDao.findByIdTeacher(idTeacher);
	}

}
