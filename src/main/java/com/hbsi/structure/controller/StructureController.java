package com.hbsi.structure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.response.Response;
import com.hbsi.structure.service.StructureServie;

/**
 * 
 * @author lijizhi
 *	显示所有结构
 */
@RestController
@RequestMapping("/structure")
public class StructureController {
	
	@Autowired
	private StructureServie structureService;
	/**
	 * 根据标准号查询所有的结构列表
	 * @param regularNum
	 * @return
	 */
	@GetMapping("/showAllStructure")
	public Response<List<?>> showAllStructure(String regularNum){
		return structureService.showAllStructureById(regularNum);		
	}
	/**
	 * 根据标准号和模糊的客户名查询客户列表
	 * @param info
	 * @return
	 */
	@PostMapping("/showAllStructureByBlurStruc")
	public Response<List<String>> showAllStructureByBlurStruc(@RequestBody String info){
		return structureService.showAllStruByBlurStruc(info);	
	}
}
