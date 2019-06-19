package com.hbsi.basedata;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hbsi.JlCheckApplication;
import com.hbsi.basedata.wireattributesybt53592010.service.WireAttributesYbt53592010Service;
import com.hbsi.basedata.wireattributesybt53592010.service.impl.WireAttributesYbt53592010ServiceImpl;
import com.hbsi.domain.WireAttributesYbt53592010;
import com.hbsi.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class WireAttrYbt59392010Test {
    @Resource
    private WireAttributesYbt53592010Service wireAttrService;
    
    @Test
	public void testSaveWireattributes() {
		WireAttributesYbt53592010 single=new WireAttributesYbt53592010();
		single.setStructure("kjkjussaf");
		single.setIntensityLow(122);
		WireAttributesYbt53592010 single1=new WireAttributesYbt53592010();
		single1.setStructure("qqwqsasdfaf");
		single1.setIntensityLow(133);
		WireAttributesYbt53592010 single2=new WireAttributesYbt53592010();
		single2.setStructure("dsxcvaf");
		single2.setIntensityLow(144);
		List<WireAttributesYbt53592010> list = new ArrayList<>();
        list.add(single);
        list.add(single2);
        list.add(single1);
        System.out.println(list);
        Response<Integer> response = wireAttrService.saveWireAttrList(list);
		System.out.println(list);
		System.out.println(response);
	}
}
