package com.example.testtask1.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@AttributeOverrides({
		@AttributeOverride(
				name = "size.width",
				column = @Column(name = "width")
		),
		@AttributeOverride(
				name = "size.height",
				column = @Column(name = "height")
		)
})
public class Item extends BaseEntity {
	private String imageUrl;
	private String name;
	private int count;
	private int weight;
	@Embedded
	private Size size;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "item_id")
	private Set<Comment> comments = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Item)) return false;

		Item item = (Item) o;

		if (getCount() != item.getCount()) return false;
		if (getWeight() != item.getWeight()) return false;
		if (getImageUrl() != null ? !getImageUrl().equals(item.getImageUrl()) : item.getImageUrl() != null)
			return false;
		if (getName() != null ? !getName().equals(item.getName()) : item.getName() != null) return false;
		return getSize() != null ? getSize().equals(item.getSize()) : item.getSize() == null;
	}
	@Override
	public int hashCode() {
		int result = getImageUrl() != null ? getImageUrl().hashCode() : 0;
		result = 31 * result + (getName() != null ? getName().hashCode() : 0);
		result = 31 * result + getCount();
		result = 31 * result + getWeight();
		result = 31 * result + (getSize() != null ? getSize().hashCode() : 0);
		return result;
	}
}
