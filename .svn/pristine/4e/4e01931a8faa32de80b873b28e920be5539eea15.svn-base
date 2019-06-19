package com.hbsi.basedata.wireattrgbt89192006.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.basedata.wireattrgbt89192006.service.WireAttributesGbt89182006Service;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.domain.WireAttributesGBT89182006;
import com.hbsi.domain.WireAttributesYbt53592010;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/WireAttributesGbt89182006")
public class WireAttributesGbt89182006Controller {

	static BaseDataImportUtil baseDataImportUtil;
	@Autowired
	private WireAttributesGbt89182006Service wireAttrService;
	
	@PostMapping("/save")
	private Response<Integer> saveWireAttribute() throws InvalidFormatException, IOException{
		String path="/Users/admin/Desktop/GB8918-2006.xls";
		List<WireAttributesGBT89182006> list = baseDataImportUtil.importWireAttributesGbt89182006(path);
		List<WireAttributesGBT89182006> list2 = baseDataImportUtil.importWireAttributesGbt891820061(path);
		wireAttrService.saveWireAttrList(list);
		return wireAttrService.saveWireAttrList(list2);
	}
	
	@PostMapping("/savesingle")
	public Response<Integer> saveSingleWireAttribute(@RequestBody WireAttributesGBT89182006 single) {
        return wireAttrService.saveSingleWireAttr(single);
	}
	@PostMapping("/update")
	public Response<Integer> updateWireAttribute(@RequestBody WireAttributesGBT89182006 single) {
        return wireAttrService.updateWireAttr(single);
	}
	@PostMapping("/select")
	public Response<List<WireAttributesGBT89182006>> selectWireAttribute() {
        return wireAttrService.selectWireAttr();
	}
	@PostMapping("/deleteall")
	public Response<Integer> deleteAllData() {
        return wireAttrService.deleteAllData();
	}
	@PostMapping("/delete")
	public Response<Integer> deleteWireAttribute(@RequestBody Integer id) {
        return wireAttrService.deleteSingleWireAttr(id);
	}
	
}
