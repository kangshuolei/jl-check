package com.hbsi.strength.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.response.Response;
import com.hbsi.strength.service.StrengthService;

@RestController
@RequestMapping("/ratedStrength")
public class StrengthController {
	
	@Autowired
	private StrengthService strengthService;
	
	/**
	 * 展示所有额定强度
	 * @return
	 */
	@RequestMapping("/showStrength")
	public Response<List<Integer>> showStrength() {		
		return strengthService.showAllStrength();
	}
}
