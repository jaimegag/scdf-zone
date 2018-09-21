package io.pivotal.datafow.samples.tasksample.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "species")
public class Species implements Serializable {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String county;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String taxonomy_g;

    @Column(nullable = false)
    private String taxonomy_sg;

    @Column(nullable = false)
    private String sci_name;

    @Column(nullable = false)
    private String common_name;

    private Integer tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
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
                ", tag=" + tag +
                '}';
    }
}
