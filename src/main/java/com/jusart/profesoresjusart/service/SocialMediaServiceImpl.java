package com.jusart.profesoresjusart.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jusart.profesoresjusart.dao.SocialMediaDao;
import com.jusart.profesoresjusart.model.SocialMedia;
import com.jusart.profesoresjusart.model.TeacherSocialMedia;


@Service("socialMediaService")
@Transactional
public class SocialMediaServiceImpl implements SocialMediaService{
	
	@Autowired
	private SocialMediaDao _socialMediaDao;

	@Override
	public void saveSocialMedia(SocialMedia socialMedia) {
		// TODO Auto-generated method stub
		_socialMediaDao.saveSocialMedia(socialMedia);
		
	}

	@Override
	public void deleteSocialMedia(Long idSocialMedia) {
		// TODO Auto-generated method stub
		_socialMediaDao.deleteSocialMedia(idSocialMedia);
	}

	@Override
	public void updateSocialMedia(SocialMedia idSocialMedia) {
		// TODO Auto-generated method stub
		_socialMediaDao.updateSocialMedia(idSocialMedia);
	}

	@Override
	public List<SocialMedia> findAllSocialMedia() {
		// TODO Auto-generated method stub
		return _socialMediaDao.findAllSocialMedia();
	}

	@Override
	public SocialMedia findById(Long idSocialMedia) {
		// TODO Auto-generated method stub
		return _socialMediaDao.findById(idSocialMedia);
	}

	@Override
	public SocialMedia findByName(String name) {
		// TODO Auto-generated method stub
		return _socialMediaDao.findByName(name);
	}

	@Override
	public TeacherSocialMedia findBySocialMediaByIdAndName(Long idSocialMedia, String nickname) {
		// TODO Auto-generated method stub
		return _socialMediaDao.findBySocialMediaByIdAndName(idSocialMedia, nickname);
	}

}
