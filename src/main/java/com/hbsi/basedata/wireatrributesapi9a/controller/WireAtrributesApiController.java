package com.hbsi.basedata.wireatrributesapi9a.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.basedata.wireatrributesapi9a.service.WireAtrributesApi9a2011Service;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.domain.WireAttributesApi9a2011;

@RestController
@RequestMapping("wireatrributesapi")
public class WireAtrributesApiController {

	@Autowired
	private WireAtrributesApi9a2011Service WireAtrributesApi9a2011Service;
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void saveWireAtrributesApi9A() throws Exception, IOException {
		String path = "/Users/admin/Desktop/API 9A 2011.xls";
		List<WireAttributesApi9a2011> list = BaseDataImportUtil.importWireAttributesApi9A(path);
		WireAtrributesApi9a2011Service.saveWireAtriibutesApi(list);
	}
}
