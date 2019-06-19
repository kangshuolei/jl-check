package com.hbsi.basedata.reversebending.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.basedata.reversebending.service.ReverseBendingService;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.domain.ReverseBending;
import com.hbsi.response.Response;

@RestController
@RequestMapping("reversebending")
public class ReverseBendingController {

	@Autowired
	private ReverseBendingService reverseBendingService;
	
	@PostMapping("/save")
	public Response<Integer> saveReverseBending() throws InvalidFormatException, IOException{
//		String path="/Users/admin/Desktop/GBT20118-2006.xls";
//		String path="/Users/admin/Desktop/J&L 10302-2018.xls";
//		String path="/Users/admin/Desktop/GBT 20067-2017.xlsx";
//		String path="/Users/admin/Desktop/GBT 20118-2017.xlsx";
//		String path="/Users/admin/Desktop/YBT5359-2010.xls";
//		String path="/Users/admin/Desktop/GB8918-2006.xls";
//		String path = "/Users/admin/Desktop/YB:T 4542-2016.xlsx";
		String path = "/Users/admin/Desktop/ISO 2408-2004.xls";
		List<ReverseBending> list= BaseDataImportUtil.importReverse(path);
		return reverseBendingService.saveReverseBending(list);
	}
	@PostMapping("/savesingle")
	public Response<Integer> saveSingleReverseBending(@RequestBody ReverseBending reverseBending) {
        return reverseBendingService.saveSingleReverseBending(reverseBending);
	}
	@PostMapping("/update")
	public Response<Integer> updateDiameterDeviation(@RequestBody ReverseBending reverseBending) {
        return reverseBendingService.updateReverseBending(reverseBending);
	}
	@PostMapping("/select")
	public Response<List<ReverseBending>> selectReverseBending() {
        return reverseBendingService.selectReverseBending();
	}
	@PostMapping("/deleteall")
	public Response<Integer> deleteAllData() {
        return reverseBendingService.deleteAllData();
	}
	@PostMapping("/delete")
	public Response<Integer> deleteReverseBending(@RequestBody Integer id) {
        return reverseBendingService.deleteReverseBending(id);
	}
}
