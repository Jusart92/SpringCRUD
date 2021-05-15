package com.jusart.profesoresjusart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.jusart.profesoresjusart.model.SocialMedia;
import com.jusart.profesoresjusart.service.SocialMediaService;

import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/v1")
public class SocialMediaController {

	@Autowired
	SocialMediaService _socialMediaService;

	// GET
	@RequestMapping(value = "/socialMedias", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<SocialMedia>> getSocialMedias() {

		List<SocialMedia> socialMedias = new ArrayList<>();
		socialMedias = _socialMediaService.findAllSocialMedia();
		if (socialMedias.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<SocialMedia>>(socialMedias, HttpStatus.OK);
	}

	// GET
	@RequestMapping(value = "/socialMedias/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<SocialMedia> getSocialMediaById(@PathVariable("id") Long idSocialMedia) {
		if (idSocialMedia == null || idSocialMedia <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		SocialMedia socialMedia = _socialMediaService.findById(idSocialMedia);
		if (socialMedia == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<SocialMedia>(socialMedia, HttpStatus.OK);
	}

	// POST
	@RequestMapping(value = "/socialMedias", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> crateSocialMedia(@RequestBody SocialMedia socialMedia,
			UriComponentsBuilder uriComponentsBuilder) {
		if (socialMedia.getName().equals(null) || socialMedia.getName().isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		if (_socialMediaService.findByName(socialMedia.getName()) != null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		_socialMediaService.saveSocialMedia(socialMedia);

		SocialMedia socialMedia2 = _socialMediaService.findByName(socialMedia.getName());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponentsBuilder.path("/v1/socialMedias/{id}")
				.buildAndExpand(socialMedia2.getIdSocialMedia()).toUri());

		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// UPDATE
	@RequestMapping(value = "/socialMedias/{id}", method = RequestMethod.PATCH, headers = "Accept=application/json")
	public ResponseEntity<?> updateSocialMedia(@PathVariable("id") Long idSocialMedia,
			@RequestBody SocialMedia socialMedia) {
		SocialMedia currentSocialMedia = _socialMediaService.findById(idSocialMedia);
		if (idSocialMedia == null || idSocialMedia <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		if (currentSocialMedia == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		currentSocialMedia.setName(socialMedia.getName());
		currentSocialMedia.setIcon(socialMedia.getIcon());

		_socialMediaService.updateSocialMedia(currentSocialMedia);
		return new ResponseEntity<SocialMedia>(currentSocialMedia, HttpStatus.OK);
	}

	// Delete
	@RequestMapping(value = "/socialMedias/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<?> deleteSocialMedia(@PathVariable("id") Long idSocialMedia) {
		if (idSocialMedia == null || idSocialMedia <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		SocialMedia socialMedia = _socialMediaService.findById(idSocialMedia);
		if(socialMedia == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		_socialMediaService.deleteSocialMedia(idSocialMedia);
		return new ResponseEntity<SocialMedia>(HttpStatus.OK);

	}

}
