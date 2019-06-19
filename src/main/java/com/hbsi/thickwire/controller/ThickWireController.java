package com.hbsi.thickwire.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.domain.WireRope;
import com.hbsi.response.Response;
import com.hbsi.thickwire.service.ThickWireService;

@RestController
@RequestMapping("/thickwire")
public class ThickWireController {

	private ThickWireService tWService;
	@PostMapping("/wireJudge")
	public Response<WireRope> wireJudge(@RequestBody WireRope wireRope){
		return tWService.judgeThickWire(wireRope);
	}
}
