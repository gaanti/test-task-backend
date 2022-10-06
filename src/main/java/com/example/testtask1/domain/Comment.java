package com.example.testtask1.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Comment extends BaseEntity{
	@JsonProperty("description")
	private String description;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Comment)) return false;

		Comment that = (Comment) o;

		return getDescription() != null ? getDescription().equals(that.getDescription()) : that.getDescription() == null;
	}

	@Override
	public int hashCode() {
		return getDescription() != null ? getDescription().hashCode() : 0;
	}
}