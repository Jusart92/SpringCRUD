package com.jusart.profesoresjusart.dao;

import java.util.List;

import com.jusart.profesoresjusart.model.Course;
import com.jusart.profesoresjusart.model.SocialMedia;
import com.jusart.profesoresjusart.model.TeacherSocialMedia;

public class SocialMediaDaoImpl extends AbstracSession implements SocialMediaDao {

	public void saveSocialMedia(SocialMedia socialMedia) {
		getSession().persist(socialMedia);
	}

	public void deleteSocialMedia(Long idSocialMedia) {
		SocialMedia socialMedia = findById(idSocialMedia);
		if (socialMedia != null) {
			getSession().delete(socialMedia);
		}
	}

	public void updateSocialMedia(SocialMedia idSocialMedia) {
		// TODO Auto-generated method stub
		getSession().update(idSocialMedia);
	}

	public List<SocialMedia> findAllSocialMedia() {
		// TODO Auto-generated method stub
		return getSession().createQuery("from SocialMedia").list();
	}

	public SocialMedia findById(Long idSocialMedia) {
		// TODO Auto-generated method stub
		return (SocialMedia) getSession().get(SocialMedia.class, idSocialMedia);
	}

	@Override
	public SocialMedia findByName(String name) {
		// TODO Auto-generated method stub
		return (SocialMedia) getSession().createQuery("from SocialMedia where name = :name").setParameter("name", name)
				.uniqueResult();
	}

	@Override
	public TeacherSocialMedia findBySocialMediaByIdAndName(Long idSocialMedia, String nickname) {
		// TODO Auto-generated method stub
		List<Object[]> objects = getSession()
				.createQuery("from TeacherSocialMedia tsm join tsm.socialMedia sm "
						+ "where sm.idSocialMedia = :idSocialMedia and tsm.nickname = :nickname")
				.setParameter("idSocialMedia", idSocialMedia).setParameter("nickname", nickname).list();

		if (objects.size() > 0) {
			for (Object[] object2 : objects) {
				for (Object object : object2) {
					if (object instanceof TeacherSocialMedia) {
						return (TeacherSocialMedia) object;
					}
				}
			}

		}
		return null;
	}

}
