package io.pivotal.dataflow.samples.domain;

import java.io.Serializable;

public class Species implements Serializable {
    private Long id;
    private String county;
    private String category;
    private String taxonomy_g;
    private String taxonomy_sg;
    private String sci_name;
    private String common_name;

    protected Species() {
    }

    public Species(Long id, String county, String category, String taxonomy_g, String taxonomy_sg, String sci_name, String common_name) {
        this.id = id;
        this.county = county;
        this.category = category;
        this.taxonomy_g = taxonomy_g;
        this.taxonomy_sg = taxonomy_sg;
        this.sci_name = sci_name;
        this.common_name = common_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTaxonomy_g() {
        return taxonomy_g;
    }

    public void setTaxonomy_g(String taxonomy_g) {
        this.taxonomy_g = taxonomy_g;
    }

    public String getTaxonomy_sg() {
        return taxonomy_sg;
    }

    public void setTaxonomy_sg(String taxonomy_sg) {
        this.taxonomy_sg = taxonomy_sg;
    }

    public String getSci_name() {
        return sci_name;
    }

    public void setSci_name(String sci_name) {
        this.sci_name = sci_name;
    }

    public String getCommon_name() {
        return common_name;
    }

    public void setCommon_name(String common_name) {
        this.common_name = common_name;
    }

    @Override
    public String toString() {
        return "Species{" +
                "id=" + id +
                ", county='" + county + '\'' +
                ", category='" + category + '\'' +
                ", taxonomy_g='" + taxonomy_g + '\'' +
                ", taxonomy_sg='" + taxonomy_sg + '\'' +
                ", sci_name='" + sci_name + '\'' +
                ", common_name='" + common_name + '\'' +
                '}';
    }
}
