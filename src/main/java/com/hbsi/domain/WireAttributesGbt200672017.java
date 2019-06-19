package com.hbsi.domain;

import java.io.Serializable;

public class WireAttributesGbt200672017 implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wire_attributes_gbt20067_2017.id
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wire_attributes_gbt20067_2017.structure
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    private String structure;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wire_attributes_gbt20067_2017.minimum_breaking_force
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    private Double minimumBreakingForce;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wire_attributes_gbt20067_2017.tanning_loss_factor
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    private Double tanningLossFactor;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wire_attributes_gbt20067_2017.state
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    private String state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table wire_attributes_gbt20067_2017
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wire_attributes_gbt20067_2017.id
     *
     * @return the value of wire_attributes_gbt20067_2017.id
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wire_attributes_gbt20067_2017.id
     *
     * @param id the value for wire_attributes_gbt20067_2017.id
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wire_attributes_gbt20067_2017.structure
     *
     * @return the value of wire_attributes_gbt20067_2017.structure
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    public String getStructure() {
        return structure;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wire_attributes_gbt20067_2017.structure
     *
     * @param structure the value for wire_attributes_gbt20067_2017.structure
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    public void setStructure(String structure) {
        this.structure = structure == null ? null : structure.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wire_attributes_gbt20067_2017.minimum_breaking_force
     *
     * @return the value of wire_attributes_gbt20067_2017.minimum_breaking_force
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    public Double getMinimumBreakingForce() {
        return minimumBreakingForce;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wire_attributes_gbt20067_2017.minimum_breaking_force
     *
     * @param minimumBreakingForce the value for wire_attributes_gbt20067_2017.minimum_breaking_force
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    public void setMinimumBreakingForce(Double minimumBreakingForce) {
        this.minimumBreakingForce = minimumBreakingForce;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wire_attributes_gbt20067_2017.tanning_loss_factor
     *
     * @return the value of wire_attributes_gbt20067_2017.tanning_loss_factor
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    public Double getTanningLossFactor() {
        return tanningLossFactor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wire_attributes_gbt20067_2017.tanning_loss_factor
     *
     * @param tanningLossFactor the value for wire_attributes_gbt20067_2017.tanning_loss_factor
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    public void setTanningLossFactor(Double tanningLossFactor) {
        this.tanningLossFactor = tanningLossFactor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wire_attributes_gbt20067_2017.state
     *
     * @return the value of wire_attributes_gbt20067_2017.state
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    public String getState() {
        return state;
    }

	/**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wire_attributes_gbt20067_2017.state
     *
     * @param state the value for wire_attributes_gbt20067_2017.state
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wire_attributes_gbt20067_2017
     *
     * @mbg.generated Thu Oct 25 16:11:15 CST 2018
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", structure=").append(structure);
        sb.append(", minimumBreakingForce=").append(minimumBreakingForce);
        sb.append(", tanningLossFactor=").append(tanningLossFactor);
        sb.append(", state=").append(state);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}