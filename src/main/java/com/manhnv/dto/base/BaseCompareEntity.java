package com.manhnv.dto.base;

public abstract class BaseCompareEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2179720741307566079L;

	public abstract Long getId();

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof BaseCompareEntity)) {
			return false;
		}
		return ((BaseCompareEntity) obj).getId() == getId();
	}

	@Override
	public int hashCode() {
		return getId() == null ? super.hashCode() : Math.toIntExact(getId());
	}
}
