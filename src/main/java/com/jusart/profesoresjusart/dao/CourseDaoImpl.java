package com.jusart.profesoresjusart.dao;

import java.util.List;

import com.jusart.profesoresjusart.model.Course;

public class CourseDaoImpl extends AbstracSession implements CourseDao{

	@Override
	public void saveCourse(Course course) {
		// TODO Auto-generated method stub
		getSession().persist(course);
		
	}

	@Override
	public void deleteCourse(Long idCourse) {
		Course course = findById(idCourse);
		if(course != null) {
			getSession().delete(course);
		}
		
	}

	@Override
	public void updateCourse(Course idCourse) {
		// TODO Auto-generated method stub
		getSession().update(idCourse);
	}

	@Override
	public List<Course> findAllCourse() {
		// TODO Auto-generated method stub
		return getSession().createQuery("from Course").list();
	}

	@Override
	public Course findById(Long idCourse) {
		// TODO Auto-generated method stub
		return (Course) getSession().get(Course.class, idCourse);
	}

	@Override
	public Course findByName(String name) {
		// TODO Auto-generated method stub
		return (Course) getSession().createQuery(
				"from Course where name = :name")
				.setParameter("name", name).uniqueResult();
	}

	@Override
	public List<Course> findByIdTeacher(Long idTeacher) {
		// TODO Auto-generated method stub
		return (List<Course>) getSession().createQuery(
				"from Course c join c.teacher t where t.idTeacher = :idTeacher")
				.setParameter("idTeacher", idTeacher).list();
	}

}
