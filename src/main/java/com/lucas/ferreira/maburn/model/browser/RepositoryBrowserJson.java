package com.lucas.ferreira.maburn.model.browser;

public class RepositoryBrowserJson {
	private String name;
	private Integer revision;
	private boolean installByDefault;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getRevision() {
		return revision;
	}
	public void setRevision(Integer revision) {
		this.revision = revision;
	}
	public boolean isInstallByDefault() {
		return installByDefault;
	}
	public void setInstallByDefault(boolean installByDefault) {
		this.installByDefault = installByDefault;
	}
	@Override
	public String toString() {
		return "RepositoryBrowserJson [name=" + name + ", revision=" + revision + ", installByDefault="
				+ installByDefault + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (installByDefault ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((revision == null) ? 0 : revision.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RepositoryBrowserJson other = (RepositoryBrowserJson) obj;
		if (installByDefault != other.installByDefault)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (revision == null) {
			if (other.revision != null)
				return false;
		} else if (!revision.equals(other.revision))
			return false;
		return true;
	}

}
