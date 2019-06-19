package com.hbsi.strength.service;

import java.util.List;

import com.hbsi.response.Response;

public interface StrengthService {
	/**
	 * 展示所有额定强度
	 * @return
	 */
	Response<List<Integer>> showAllStrength();
}
