package com.hiersun.jewelry.api.entity;

public class VersionInfo {
	private String version;

	private String versionDes;

	private boolean force;

	private String downUrl;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersionDes() {
		return versionDes;
	}

	public void setVersionDes(String versionDes) {
		this.versionDes = versionDes;
	}

	public boolean isForce() {
		return force;
	}

	public void setForce(boolean force) {
		this.force = force;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

}
