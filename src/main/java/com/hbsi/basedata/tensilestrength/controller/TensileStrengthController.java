package com.hbsi.basedata.tensilestrength.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.basedata.tensilestrength.service.TensileStrengthService;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.domain.TensileStrength;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/tensilestrength")
public class TensileStrengthController {

	@Autowired
	private TensileStrengthService tensileStrengthService;
	static BaseDataImportUtil baseDataImportUtil;
	
	@PostMapping("/save")
	public Response<Integer> saveTensileStrength() throws InvalidFormatException, IOException{
//		String path="/Users/admin/Desktop/GBT20118-2006.xls";
		String path="/Users/admin/Desktop/GBT 20118-2017.xlsx";
		List<TensileStrength> list= baseDataImportUtil.importTensileStrength(path);
		return tensileStrengthService.saveTensileStrength(list);
	}
	@PostMapping("/savesingle")
	public Response<Integer> saveSingleTensileStrength(@RequestBody TensileStrength tensileStrength) {
        return tensileStrengthService.saveSingleTensileStrength(tensileStrength);
	}
	@PostMapping("/update")
	public Response<Integer> updateDiameterDeviation(@RequestBody TensileStrength tensileStrength) {
        return tensileStrengthService.updateTensileStrength(tensileStrength);
	}
	@PostMapping("/select")
	public Response<List<TensileStrength>> selectTensileStrength() {
        return tensileStrengthService.selectTensileStrength();
	}
	@PostMapping("/deleteall")
	public Response<Integer> deleteAllData() {
        return tensileStrengthService.deleteAllData();
	}
	@PostMapping("/delete")
	public Response<Integer> deleteReverseBending(@RequestBody Integer id) {
        return tensileStrengthService.deleteTensileStrength(id);
	}
}
