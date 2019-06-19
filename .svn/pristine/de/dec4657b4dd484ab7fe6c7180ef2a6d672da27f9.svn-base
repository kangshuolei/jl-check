package com.hbsi.basedata.zinclayerweight.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.basedata.zinclayerweight.service.ZincLayerWeightService;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.domain.ReverseBending;
import com.hbsi.domain.ZincLayerWeight;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/zinclayerweight")
public class ZincLayerWeightController {

	@Autowired
	private ZincLayerWeightService zincLayerWeightService;
	static BaseDataImportUtil baseDataImportUtil;
	
	@PostMapping("/save")
	public Response<Integer> saveZincLayerWeight() throws InvalidFormatException, IOException{
//		String path="/Users/admin/Desktop/GBT20118-2006.xls";
//		String path="/Users/admin/Desktop/YBT5359-2010.xls";
		String path="/Users/admin/Desktop/GB8918-2006.xls";
		List<ZincLayerWeight> list= baseDataImportUtil.importZincLayerWeight(path);
		return zincLayerWeightService.saveZincLayerWeight(list);
	}
	@PostMapping("/savesingle")
	public Response<Integer> saveSingleZincLayerWeight(@RequestBody ZincLayerWeight zincLayerWeight) {
        return zincLayerWeightService.saveSingleZincLayerWeight(zincLayerWeight);
	}
	@PostMapping("/update")
	public Response<Integer> updateZincLayerWeight(@RequestBody ZincLayerWeight zincLayerWeight) {
        return zincLayerWeightService.updateZincLayerWeight(zincLayerWeight);
	}
	@PostMapping("/select")
	public Response<List<ZincLayerWeight>> selectZincLayerWeight() {
        return zincLayerWeightService.selectZincLayerWeight();
	}
	@PostMapping("/deleteall")
	public Response<Integer> deleteAllData() {
        return zincLayerWeightService.deleteAllData();
	}
	@PostMapping("/delete")
	public Response<Integer> deleteZincLayerWeight(@RequestBody Integer id) {
        return zincLayerWeightService.deleteZincLayerWeight(id);
	}
}
