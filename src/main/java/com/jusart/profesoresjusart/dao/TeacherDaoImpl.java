package com.jusart.profesoresjusart.dao;

import java.util.Iterator;
import java.util.List;

import com.jusart.profesoresjusart.model.Teacher;
import com.jusart.profesoresjusart.model.TeacherSocialMedia;

public class TeacherDaoImpl extends AbstracSession implements TeacherDao {

	
	public void saveTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		getSession().persist(teacher);

	}

	
	public void deleteTeacherById(Long idTeacher) {
		// TODO Auto-generated method stub
		Teacher teacher = findById(idTeacher);
		if (teacher != null) {
			
			Iterator<TeacherSocialMedia> i = teacher.getTeacherSocialMedia().iterator();
			
			while(i.hasNext()) {
				TeacherSocialMedia teacherSocialMedia = i.next();
				i.remove();
				getSession().delete(teacherSocialMedia);
			}
			
			teacher.getTeacherSocialMedia().clear();
			getSession().delete(teacher);
		}
	}

	
	public void updateTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		getSession().update(teacher);

	}

	
	public List<Teacher> findAllTeachers() {
		// Se utilizara HQL
		return getSession().createQuery("from Teacher").list();
	}

	
	public Teacher findById(Long idTeacher) {
		// TODO Auto-generated method stub
		return (Teacher) getSession().get(Teacher.class, idTeacher);
	}

	
	public Teacher findByName(String name) {
		// TODO Auto-generated method stub
		return (Teacher) getSession().createQuery(
				"From Teacher where name = :name").setParameter("name", name).uniqueResult();
	}

}
