package com.jeromesimmonds.phonebook.core.be;

/**
 * @author Jerome Simmonds
 *
 */
public enum FindFilterMode {
	Eq,
	NotEq,
	In,
	NotIn,
	IsNull,
	IsNotNull,
	GreaterThan,
	GreaterOrEq,
	LessThan,
	LessOrEq,
	Like,
	NotLike
}
