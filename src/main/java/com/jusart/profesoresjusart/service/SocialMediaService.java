package com.jusart.profesoresjusart.service;

import java.util.List;

import com.jusart.profesoresjusart.model.SocialMedia;
import com.jusart.profesoresjusart.model.TeacherSocialMedia;

public interface SocialMediaService {

	void saveSocialMedia(SocialMedia socialMedia);

	void deleteSocialMedia(Long idSocialMedia);

	void updateSocialMedia(SocialMedia idSocialMedia);

	List<SocialMedia> findAllSocialMedia();

	SocialMedia findById(Long idSocialMedia);
	
	SocialMedia findByName (String name);
	
	TeacherSocialMedia findBySocialMediaByIdAndName(Long idSocialMedia, String nickname);
	
	TeacherSocialMedia findSocialMediaByIdTeacherAndIdSocialMedia(Long idTeacher, Long idSocialMedia);
}
