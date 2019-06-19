package com.hbsi.basedata.wireattributesybt53592010.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.basedata.wireattributesgbt200672017.service.WireAttributesService;
import com.hbsi.basedata.wireattributesybt53592010.service.WireAttributesYbt53592010Service;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.domain.WireAttributesGbt200672017;
import com.hbsi.domain.WireAttributesYbt53592010;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/wireattributesybt53592010")
public class WireAttributesYbt53592010Controller {

	static BaseDataImportUtil baseDataImportUtil;
	@Autowired
	private WireAttributesYbt53592010Service wireAttributesService ;
	
	@PostMapping("/save")
	private Response<Integer> saveWireAttribute() throws InvalidFormatException, IOException{
		String path="/Users/admin/Desktop/YBT5359-2010.xls";
		List<WireAttributesYbt53592010> list = baseDataImportUtil.importWireAttributesYbt53592010(path);
		System.out.println(list);
		return wireAttributesService.saveWireAttrList(list);
	}
	
	@PostMapping("/savesingle")
	public Response<Integer> saveSingleWireAttribute(@RequestBody WireAttributesYbt53592010 single) {
        return wireAttributesService.saveWireAttr(single);
	}
	@PostMapping("/update")
	public Response<Integer> updateWireAttribute(@RequestBody WireAttributesYbt53592010 single) {
        return wireAttributesService.updateWireAttr(single);
	}
	@PostMapping("/select")
	public Response<List<WireAttributesYbt53592010>> selectWireAttribute() {
        return wireAttributesService.selectWireAttrList();
	}
	@PostMapping("/deleteall")
	public Response<Integer> deleteAllData() {
        return wireAttributesService.deleteWireAttr();
	}
	@PostMapping("/delete")
	public Response<Integer> deleteWireAttribute(@RequestBody Integer id) {
        return wireAttributesService.deleteWireAttr(id);
	}
}
