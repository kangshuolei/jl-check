package com.hbsi.basedata.wireattributesgbt200672017.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.basedata.wireattributesgbt200672017.service.WireAttributesService;
import com.hbsi.basedata.wireattributesgbt201182017.service.Wireattributesgbt201182017Service;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.domain.WireAttributesGbt200672017;
import com.hbsi.domain.WireAttributesGbt201182017;
import com.hbsi.domain.ZincLayerWeight;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/wireattributesgbt200672017")
public class Wireattributesgbt200672017Controller {

	@Autowired
	private WireAttributesService wireAttributesService ;
	static BaseDataImportUtil baseDataImportUtil;
	
	@PostMapping("/save")
	private Response<Integer> saveWireAttribute() throws InvalidFormatException, IOException{
		String path="/Users/admin/Desktop/GBT 20067-2017.xlsx";
		List<WireAttributesGbt200672017> list = baseDataImportUtil.importWireAttributesGbt200672017(path);
		return wireAttributesService.saveWireattributes(list);
	}
	
	@PostMapping("/savesingle")
	public Response<Integer> saveSingleWireAttribute(@RequestBody WireAttributesGbt200672017 single) {
        return wireAttributesService.saveSingleWireattribute(single);
	}
	@PostMapping("/update")
	public Response<Integer> updateWireAttribute(@RequestBody WireAttributesGbt200672017 single) {
        return wireAttributesService.updateWireattributes(single);
	}
	@PostMapping("/select")
	public Response<List<WireAttributesGbt200672017>> selectWireAttribute() {
        return wireAttributesService.selectWireattributes();
	}
	@PostMapping("/deleteall")
	public Response<Integer> deleteAllData() {
        return wireAttributesService.deleteAllData();
	}
	@PostMapping("/delete")
	public Response<Integer> deleteWireAttribute(@RequestBody Integer id) {
        return wireAttributesService.deleteWireattributes(id);
	}
}
