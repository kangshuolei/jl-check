package com.hbsi.basedata.fromvalue.controller;


import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.basedata.fromvalue.service.FromValueService;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.domain.FromValue;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/fromvalue")
public class FromValueController {

	@Autowired
	private FromValueService fromValueService;
	
	@PostMapping("/save")
	public Response<Integer> saveFromValue() throws InvalidFormatException, IOException{
		
//		String path="/Users/admin/Desktop/GBT20118-2006.xls";
//		String path="/Users/admin/Desktop/YBT5359-2010.xls";
		String path="/Users/admin/Desktop/ISO 2408-2004.xls";
		List<FromValue> list= BaseDataImportUtil.importFromValue(path);
        Response<Integer> response = fromValueService.saveFromValue(list);
		return response;
		
	}
	@PostMapping("/savesingle")
	public Response<Integer> saveSingleFromValue(@RequestBody FromValue fromValue) {
        return fromValueService.saveSingleFromValue(fromValue);
	}
	@PostMapping("/update")
	public Response<Integer> updateFromValue(@RequestBody FromValue fromValue) {
        return fromValueService.updateFromValue(fromValue);
	}
	@PostMapping("/select")
	public Response<List<FromValue>> selectFromValue() {
        return fromValueService.selectFromValue();
	}
	@PostMapping("/deleteall")
	public Response<Integer> deleteAllData() {
        return fromValueService.deleteAllData();
	}
	@PostMapping("/delete")
	public Response<Integer> deleteFromValue(@RequestBody Integer id) {
        return fromValueService.deleteFromValue(id);
	}
}
