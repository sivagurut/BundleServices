package com.directv.bundlesIntegration.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.TreeMap;

// TODO: Auto-generated Javadoc
/**
 * The Class FieldComparator.
 */
@SuppressWarnings("unchecked")
public class FieldComparator implements Comparator, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3379116697724547202L;

	/** The sort by. */
	private String sortBy = "";

	/** The default sort by. */
	private String defaultSortBy = "";

	/** The order by. */
	private String orderBy = "ASC";

	/**
	 * Instantiates a new field comparator.
	 * 
	 * @param feild the feild
	 * @param defaultField the default field
	 */
	public FieldComparator(String feild, String defaultField) {
		sortBy = feild;
		defaultSortBy = defaultField;
	}

	/**
	 * Instantiates a new field comparator.
	 * 
	 * @param feild the feild
	 * @param defaultField the default field
	 * @param orderBy the order by
	 */
	public FieldComparator(String feild, String defaultField, String orderBy) {
		sortBy = feild;
		defaultSortBy = defaultField;
		this.orderBy = orderBy;
	}

	/**
	 * Instantiates a new field comparator.
	 */
	@SuppressWarnings("unused")
	private FieldComparator() {
	}

	/**
	 * Overridden Method
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public int compare(Object o1, Object o2) {
		Object value1 = null;
		Object value2 = null;

		TreeMap propertiesA = new TreeMap(String.CASE_INSENSITIVE_ORDER);
		propertiesA.putAll(ReflectionUtil.describe(o1));

		TreeMap propertiesB = new TreeMap(String.CASE_INSENSITIVE_ORDER);
		propertiesB.putAll(ReflectionUtil.describe(o2));

		int result = compareFields(sortBy, orderBy, propertiesA, propertiesB, value1, value2);

		if (result == 0) {
			result = compareFields(defaultSortBy, orderBy, propertiesA, propertiesB, value1, value2);
		}
		return result;
	}

	/**
	 * Compare fields.
	 * 
	 * @param fieldToSortBy the field to sort by
	 * @param orderBy the order by
	 * @param propertiesA the properties a
	 * @param propertiesB the properties b
	 * @param value1 the value1
	 * @param value2 the value2
	 * @return the int
	 */
	private int compareFields(String fieldToSortBy, String orderBy, TreeMap propertiesA, TreeMap propertiesB, Object value1, Object value2) {
		int result = 0;
		if (propertiesA.containsKey(fieldToSortBy) && propertiesB.containsKey(fieldToSortBy)) {
			if (propertiesA.get(fieldToSortBy) != null) {
				value1 = propertiesA.get(fieldToSortBy);
			}
			if (propertiesB.get(fieldToSortBy) != null) {
				value2 = propertiesB.get(fieldToSortBy);
			}

			if (orderBy.equals("ASC")) {
				if (value1 == null && value2 == null) {
					return 0;
				} else if (value1 == null && value2 != null) {
					return -1;
				} else if (value1 != null && value2 == null) {
					return 1;
				} else if (value1 instanceof Comparable && value2 instanceof Comparable) {
					result = ((Comparable) value1).compareTo(value2);
				}
			} else if (orderBy.equals("DESC")) {
				if (value1 == null && value2 == null) {
					return 0;
				} else if (value1 != null && value2 == null) {
					return -1;
				} else if (value1 == null && value2 != null) {
					return 1;
				} else if (value1 instanceof Comparable && value2 instanceof Comparable) {
					result = ((Comparable) value2).compareTo(value1);
				}
			}
		}
		return result;
	}
}