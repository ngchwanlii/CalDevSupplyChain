package com.caldevsupplychain.account.vo;

public enum PermissionName {
	ACCOUNT_READ("account:read"),
	ACCOUNT_UPDATE("account:update"),
	ACCOUNT_ADMIN("account:admin");

	private final String perm;

	PermissionName(String s) {
		this.perm = s;
	}

	@Override
	public String toString() {
		return this.perm;
	}
}
