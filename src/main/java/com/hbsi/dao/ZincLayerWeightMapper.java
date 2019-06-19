package com.hbsi.dao;

import java.util.List;

import com.hbsi.domain.ZincLayerWeight;

public interface ZincLayerWeightMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zinc_layer_weight
     *
     * @mbg.generated Wed Sep 26 15:54:22 CST 2018
     */
    int deleteByPrimaryKey(Integer id);
    /**
     * 删除整张表数据
     * @return
     */
    int deleteAllData();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zinc_layer_weight
     *
     * @mbg.generated Wed Sep 26 15:54:22 CST 2018
     */
    int insert(ZincLayerWeight record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zinc_layer_weight
     *
     * @mbg.generated Wed Sep 26 15:54:22 CST 2018
     */
    int insertSelective(ZincLayerWeight record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zinc_layer_weight
     *
     * @mbg.generated Wed Sep 26 15:54:22 CST 2018
     */
    ZincLayerWeight selectByPrimaryKey(Integer id);
    /**
     * 查找得到整张锌层重量表
     * @return
     */
    List<ZincLayerWeight> selectZincLayerWeight();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zinc_layer_weight
     *
     * @mbg.generated Wed Sep 26 15:54:22 CST 2018
     */
    int updateByPrimaryKeySelective(ZincLayerWeight record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zinc_layer_weight
     *
     * @mbg.generated Wed Sep 26 15:54:22 CST 2018
     */
    int updateByPrimaryKey(ZincLayerWeight record);
}