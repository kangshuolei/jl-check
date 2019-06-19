package com.hbsi.domain;

import java.io.Serializable;

public class WireAttributesApi9a2011 implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wire_attributes_api_9a_2011.id
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wire_attributes_api_9a_2011.structure
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    private String structure;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wire_attributes_api_9a_2011.minimum_breaking_force
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    private Double minimumBreakingForce;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wire_attributes_api_9a_2011.filling_factor
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    private Double fillingFactor;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wire_attributes_api_9a_2011.state
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    private String state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table wire_attributes_api_9a_2011
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wire_attributes_api_9a_2011.id
     *
     * @return the value of wire_attributes_api_9a_2011.id
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wire_attributes_api_9a_2011.id
     *
     * @param id the value for wire_attributes_api_9a_2011.id
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wire_attributes_api_9a_2011.structure
     *
     * @return the value of wire_attributes_api_9a_2011.structure
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    public String getStructure() {
        return structure;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wire_attributes_api_9a_2011.structure
     *
     * @param structure the value for wire_attributes_api_9a_2011.structure
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    public void setStructure(String structure) {
        this.structure = structure == null ? null : structure.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wire_attributes_api_9a_2011.minimum_breaking_force
     *
     * @return the value of wire_attributes_api_9a_2011.minimum_breaking_force
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    public Double getMinimumBreakingForce() {
        return minimumBreakingForce;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wire_attributes_api_9a_2011.minimum_breaking_force
     *
     * @param minimumBreakingForce the value for wire_attributes_api_9a_2011.minimum_breaking_force
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    public void setMinimumBreakingForce(Double minimumBreakingForce) {
        this.minimumBreakingForce = minimumBreakingForce;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wire_attributes_api_9a_2011.filling_factor
     *
     * @return the value of wire_attributes_api_9a_2011.filling_factor
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    public Double getFillingFactor() {
        return fillingFactor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wire_attributes_api_9a_2011.filling_factor
     *
     * @param fillingFactor the value for wire_attributes_api_9a_2011.filling_factor
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    public void setFillingFactor(Double fillingFactor) {
        this.fillingFactor = fillingFactor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wire_attributes_api_9a_2011.state
     *
     * @return the value of wire_attributes_api_9a_2011.state
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    public String getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wire_attributes_api_9a_2011.state
     *
     * @param state the value for wire_attributes_api_9a_2011.state
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wire_attributes_api_9a_2011
     *
     * @mbg.generated Thu Feb 21 16:49:36 CST 2019
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
        sb.append(", fillingFactor=").append(fillingFactor);
        sb.append(", state=").append(state);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}