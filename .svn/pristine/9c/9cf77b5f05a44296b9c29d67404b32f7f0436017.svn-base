package com.hbsi.basedata.wireattributesgbt201182017.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.basedata.wireattributesgbt201182017.service.Wireattributesgbt201182017Service;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.domain.WireAttributesGbt201182017;
import com.hbsi.domain.ZincLayerWeight;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/wireattributes")
public class Wireattributesgbt201182017Controller {

	@Autowired
	private Wireattributesgbt201182017Service wireattributesgbt201182017Service;
	static BaseDataImportUtil baseDataImportUtil;
	
	@PostMapping("/save")
	private Response<Integer> saveWireAttribute() throws InvalidFormatException, IOException{
		String path="/Users/admin/Desktop/GBT 20118-2017.xlsx";
		List<WireAttributesGbt201182017> list = baseDataImportUtil.importWireAttributesGbt2011820171(path);
		return wireattributesgbt201182017Service.saveWireattributes(list);
	}
	
	@PostMapping("/savesingle")
	public Response<Integer> saveSingleWireAttribute(@RequestBody WireAttributesGbt201182017 single) {
        return wireattributesgbt201182017Service.saveSingleWireattribute(single);
	}
	@PostMapping("/update")
	public Response<Integer> updateWireAttribute(@RequestBody WireAttributesGbt201182017 single) {
        return wireattributesgbt201182017Service.updateWireattributes(single);
	}
	@PostMapping("/select")
	public Response<List<WireAttributesGbt201182017>> selectWireAttribute() {
        return wireattributesgbt201182017Service.selectWireattributes();
	}
	@PostMapping("/deleteall")
	public Response<Integer> deleteAllData() {
        return wireattributesgbt201182017Service.deleteAllData();
	}
	@PostMapping("/delete")
	public Response<Integer> deleteWireAttribute(@RequestBody Integer id) {
        return wireattributesgbt201182017Service.deleteWireattributes(id);
	}
}
