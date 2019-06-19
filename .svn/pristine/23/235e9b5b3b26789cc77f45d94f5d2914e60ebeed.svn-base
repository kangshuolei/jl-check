package com.hbsi.basedata.steeltensilestrength.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.basedata.steeltensilestrength.service.SteelTSservice;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.domain.SteelTensileStrength;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/steelts")
public class SteelTSController {

	@Autowired
	private SteelTSservice steelTSservice;
	private BaseDataImportUtil baseDataImportUtil;
	
	@PostMapping("/save")
	public Response<Integer> saveSteelTS() throws InvalidFormatException, IOException{
		String path="/Users/admin/Desktop/J&L 10302-2018.xls";
		List<SteelTensileStrength> list = baseDataImportUtil.importSteelTensileStrength(path);
		return steelTSservice.saveSteelTS(list);
	}
	@PostMapping("/savesingle")
	public Response<Integer> saveSingleSteelTS(SteelTensileStrength steelTensileStrength){
		return steelTSservice.saveSingleSteelTS(steelTensileStrength);
	}
	@PostMapping("/update")
	public Response<Integer> updateSteelTS(SteelTensileStrength steelTensileStrength){
		return steelTSservice.updateSteelTS(steelTensileStrength);
	}
	@PostMapping("/delete")
	public Response<Integer> deleteSteelTS(Integer id){
		return steelTSservice.deleteSteelTS(id);
	}
	@PostMapping("/deleteall")
	public Response<Integer> deleteAllData(){
		return steelTSservice.deleteAllData();
	}
}
