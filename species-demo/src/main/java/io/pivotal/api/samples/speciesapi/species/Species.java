package io.pivotal.api.samples.speciesapi.species;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Species {

	@Id @GeneratedValue
	Long id;
	String county;
	String category;
	String taxonomyGroup;
	String taxonomySubgroup;
	String scientificName;
	String commonName;

	public Species() {}

	// below is just boilerplate getter/setter (consider lombok or Kotlin greeting classes)

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

	public String getTaxonomyGroup() {
		return taxonomyGroup;
	}

	public void setTaxonomyGroup(String taxonomyGroup) {
		this.taxonomyGroup = taxonomyGroup;
	}

	public String getTaxonomySubgroup() {
		return taxonomySubgroup;
	}

	public void setTaxonomySubgroup(String taxonomySubgroup) {
		this.taxonomySubgroup = taxonomySubgroup;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
}
