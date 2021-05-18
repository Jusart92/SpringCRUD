package com.jusart.profesoresjusart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.init.AbstractRepositoryPopulatorFactoryBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.jusart.profesoresjusart.model.Teacher;
import com.jusart.profesoresjusart.service.TeacherService;
import com.jusart.profesoresjusart.util.CustomErrorType;

@Controller
@RequestMapping(value = "/v1")
public class TeacherController {

	@Autowired
	TeacherService _teacherService;

	// GET
	@RequestMapping(value = "/teachers", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<Teacher>> getTeachers(@RequestParam(value = "name", required = false) String name) {

		List<Teacher> teachers = new ArrayList<>();
		
		if(name == null) {
			teachers = _teacherService.findAllTeachers();
			if (teachers.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Teacher>>(teachers, HttpStatus.OK);
		}else {
			Teacher teacher = _teacherService.findByName(name);
			if (teacher == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			
			teachers.add(teacher);
			
			return new ResponseEntity<List<Teacher>>(teachers, HttpStatus.OK); 
		}
		
		
	}

	// GET
	@RequestMapping(value = "/teachers/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") Long idTeacher) {
		if (idTeacher == null || idTeacher <= 0) {
			return new ResponseEntity(new CustomErrorType("idTeacher is required"), HttpStatus.CONFLICT);
		}
		Teacher teacher = _teacherService.findById(idTeacher);
		if (teacher == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
	}

	// POST
	@RequestMapping(value = "/teachers", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> crateTeacher(@RequestBody Teacher teacher,
			UriComponentsBuilder uriComponentsBuilder) {
		if (teacher.getName().equals(null) || teacher.getName().isEmpty()) {
			new ResponseEntity(new CustomErrorType("Teacher name is required"), HttpStatus.CONFLICT);
		}

		if (_teacherService.findByName(teacher.getName()) != null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		_teacherService.saveTeacher(teacher);

		Teacher teacher2 = _teacherService.findByName(teacher.getName());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponentsBuilder.path("/v1/teachers/{id}")
				.buildAndExpand(teacher2.getIdTeacher()).toUri());

		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// UPDATE
	@RequestMapping(value = "/teachers/{id}", method = RequestMethod.PATCH, headers = "Accept=application/json")
	public ResponseEntity<?> updateTeacher(@PathVariable("id") Long idTeacher,
			@RequestBody Teacher teacher) {
		Teacher currentTeacher = _teacherService.findById(idTeacher);
		if (idTeacher == null || idTeacher <= 0) {
			new ResponseEntity(new CustomErrorType("idTeacher is required"), HttpStatus.CONFLICT);
		}

		if (currentTeacher == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		currentTeacher.setName(teacher.getName());
		currentTeacher.setAvatar(teacher.getAvatar());

		_teacherService.updateTeacher(currentTeacher);
		return new ResponseEntity<Teacher>(currentTeacher, HttpStatus.OK);
	}

	// Delete
	@RequestMapping(value = "/teachers/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<?> deleteTeacher(@PathVariable("id") Long idTeacher) {
		if (idTeacher == null || idTeacher <= 0) {
			new ResponseEntity(new CustomErrorType("idTeacher is required"), HttpStatus.CONFLICT);
		}
		
		Teacher teacher = _teacherService.findById(idTeacher);
		if(teacher == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		_teacherService.deleteTeacherById(idTeacher);
		return new ResponseEntity<Teacher>(HttpStatus.OK);

	}
	
	public static final String TEACHER_UPLOADED_FOLDER = "images/teachers/";
	
	//CREATE TEACHER IMAGE
	@RequestMapping(value = "/teachers/images", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public ResponseEntity<byte[]>uploadTeacherImage(
			@RequestParam("id_teacher")Long idTeacher,
			@RequestParam("file")MultipartFile multipartFile,
			UriComponentsBuilder uriBucomponentsBuilder
			){
		if(idTeacher == null) {
			return new ResponseEntity(new CustomErrorType("Please set id_teacher"), HttpStatus.NO_CONTENT);
		}
		
		if(multipartFile.isEmpty()) {
			return new ResponseEntity(new CustomErrorType("Please select a file to upload"), HttpStatus.NO_CONTENT);
		}
		Teacher teacher = _teacherService.findById(idTeacher);
		if(teacher == null) {
			return new ResponseEntity(new CustomErrorType("Teacher with id_teacher: " + idTeacher.toString() + " not found"), HttpStatus.NO_CONTENT);
		}
		
		if (!teacher.getAvatar().isEmpty() || teacher.getAvatar() != null) {
			String fileName = teacher.getAvatar();
			Path path = Paths.get(fileName);
			File f = path.toFile();
			if(f.exists()) {
				f.delete();
			}
		}
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String dateName = dateFormat.format(date);
			
			String fileName = String.valueOf(idTeacher)+ "-pictureTeacher-" + dateName + "." + multipartFile.getContentType().split("/")[1];
			teacher.setAvatar(TEACHER_UPLOADED_FOLDER + fileName);
			
			byte[] bytes = multipartFile.getBytes();
			Path path = Paths.get(TEACHER_UPLOADED_FOLDER + fileName);
			Files.write(path, bytes);
			
			_teacherService.updateTeacher(teacher);
			
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity(new CustomErrorType("Error durong upload: " + multipartFile.getOriginalFilename()), HttpStatus.NO_CONTENT);

		}
		
	}
	
	//GET IMGAE
	@RequestMapping(value = "/teachers/{id_teacher}/images", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getTeacherImages(@PathVariable("id_teacher")Long idTeacher){
		if(idTeacher == null) {
			return new ResponseEntity(new CustomErrorType("Please set id_teacher"), HttpStatus.NO_CONTENT);
		}
		
		Teacher teacher = _teacherService.findById(idTeacher);
		
		if(teacher == null) {
			return new ResponseEntity(new CustomErrorType("Teacher with id_teacher: "+ idTeacher + " not found"), HttpStatus.NO_CONTENT);
		}
		try {
			String fileName = teacher.getAvatar();
			Path path = Paths.get(fileName);
			File f = path.toFile();
			if (!f.exists()) {
				return new ResponseEntity(new CustomErrorType("Image not found"), HttpStatus.NO_CONTENT);
			}
			
			byte[] image = Files.readAllBytes(path);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new CustomErrorType("Error to show image"), HttpStatus.NO_CONTENT);
		}
	}
}
