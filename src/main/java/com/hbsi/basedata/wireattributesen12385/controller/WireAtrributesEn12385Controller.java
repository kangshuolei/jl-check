package com.hbsi.basedata.wireattributesen12385.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.basedata.wireattributesen12385.service.impl.Wireatrributesen12385Impl;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.domain.WireAttributesEn123852002;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/en12385")
public class WireAtrributesEn12385Controller {

	@Autowired
	private Wireatrributesen12385Impl wireatrributesen12385;
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public Response<?> saveWireAtrributes() {
		String path = "/Users/admin/Desktop/EN12385-4-2002.xls";
		try {
//			List<WireAttributesEn123852002> list = BaseDataImportUtil.importWireAttributesEn123852002(path);
//			wireatrributesen12385.saveWireAtrributes(list);
		}  catch (Exception e) {
			e.printStackTrace();
			return new Response<>("000012000","错了，一切都错了",null);
		}
		return new Response<>("200","0k",null);
	}
	
}
