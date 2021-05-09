package com.jusart.profesoresjusart.dao;

import java.util.List;

import com.jusart.profesoresjusart.model.SocialMedia;
import com.jusart.profesoresjusart.model.TeacherSocialMedia;

public interface SocialMediaDao {

	void saveSocialMedia(SocialMedia socialMedia);

	void deleteSocialMedia(Long idSocialMedia);

	void updateSocialMedia(SocialMedia idSocialMedia);

	List<SocialMedia> findAllSocialMedia();

	SocialMedia findById(Long idSocialMedia);
	
	SocialMedia findByName (String name);
	
	TeacherSocialMedia findBySocialMediaByIdAndName(Long idSocialMedia, String nickname);

}
