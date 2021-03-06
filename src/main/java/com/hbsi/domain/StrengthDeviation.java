package com.hbsi.domain;

import java.io.Serializable;

public class StrengthDeviation implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strength_deviation.id
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strength_deviation.standard_num
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    private String standardNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strength_deviation.diamate_min
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    private Double diamateMin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strength_deviation.diamate_max
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    private Double diamateMax;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strength_deviation.value
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    private Double value;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strength_deviation.state
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    private String state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table strength_deviation
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strength_deviation.id
     *
     * @return the value of strength_deviation.id
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strength_deviation.id
     *
     * @param id the value for strength_deviation.id
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strength_deviation.standard_num
     *
     * @return the value of strength_deviation.standard_num
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    public String getStandardNum() {
        return standardNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strength_deviation.standard_num
     *
     * @param standardNum the value for strength_deviation.standard_num
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    public void setStandardNum(String standardNum) {
        this.standardNum = standardNum == null ? null : standardNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strength_deviation.diamate_min
     *
     * @return the value of strength_deviation.diamate_min
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    public Double getDiamateMin() {
        return diamateMin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strength_deviation.diamate_min
     *
     * @param diamateMin the value for strength_deviation.diamate_min
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    public void setDiamateMin(Double diamateMin) {
        this.diamateMin = diamateMin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strength_deviation.diamate_max
     *
     * @return the value of strength_deviation.diamate_max
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    public Double getDiamateMax() {
        return diamateMax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strength_deviation.diamate_max
     *
     * @param diamateMax the value for strength_deviation.diamate_max
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    public void setDiamateMax(Double diamateMax) {
        this.diamateMax = diamateMax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strength_deviation.value
     *
     * @return the value of strength_deviation.value
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    public Double getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strength_deviation.value
     *
     * @param value the value for strength_deviation.value
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strength_deviation.state
     *
     * @return the value of strength_deviation.state
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    public String getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strength_deviation.state
     *
     * @param state the value for strength_deviation.state
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table strength_deviation
     *
     * @mbg.generated Wed Oct 31 11:06:55 CST 2018
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", standardNum=").append(standardNum);
        sb.append(", diamateMin=").append(diamateMin);
        sb.append(", diamateMax=").append(diamateMax);
        sb.append(", value=").append(value);
        sb.append(", state=").append(state);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}