package com.intigna.services.canary.rest.data;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Manifest-Version: 1.0
Implementation-Title: canary-services
buildNumber: 
Implementation-Version: 0.0.1-SNAPSHOT
Built-By: jenkins
softwareVersion: 0.0.1-SNAPSHOT
Specification-Title: canary-services
Implementation-Vendor-Id: com.intigna.services.canary
buildId: 
gitBranch: 
softwareType: DockerService
buildTime: 20170913-0946
gitUrl: 
Created-By: Apache Maven 3.1.1
Build-Jdk: 1.8.0_131
Specification-Version: 0.0
Implementation-URL: http://www.sematree.io/canary-services
softwareCode: canary-services
gitCommit: 

 * @author adevoe
 *
 */
public class ServiceManifest {
	public String getBuildId() {
		return buildId;
	}
	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}
	public String getBuildNumber() {
		return buildNumber;
	}
	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}
	public String getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}
	public String getBuildJdk() {
		return buildJdk;
	}
	public void setBuildJdk(String buildJdk) {
		this.buildJdk = buildJdk;
	}
	public String getGitUrl() {
		return gitUrl;
	}
	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}
	public String getGitBranch() {
		return gitBranch;
	}
	public void setGitBranch(String gitBranch) {
		this.gitBranch = gitBranch;
	}
	public String getSoftwareVersion() {
		return softwareVersion;
	}
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
	private String buildId;
	private String buildNumber;
	private String buildTime ;
	private String buildJdk;
	private String gitUrl;
	private String gitBranch;
	private String softwareVersion;
	
	public static ServiceManifest populate(String name, Manifest manifest) {
		ServiceManifest m = new ServiceManifest();
		Attributes a = manifest.getMainAttributes();
		m.buildId = a.getValue("buildId");
		m.buildNumber = a.getValue("buildNumber");
		m.buildTime = a.getValue("buildTime");
		m.buildJdk = a.getValue("Build-Jdk");
		m.gitUrl = a.getValue("gitUrl");
		m.gitBranch = a.getValue("gitBranch");
		m.softwareVersion = a.getValue("softwareVersion");
		_manifestMap.put(name,  m);
		_name = name;
		return m;
	}
	
	private static Map<String, ServiceManifest> _manifestMap = new HashMap<>();
	private static String _name = null;
	public static ServiceManifest getTheDefaultServiceManifest() {
		return getTheServiceManifest(null);
	}
	public static ServiceManifest getTheServiceManifest(String name) {
		if (name == null) {
			name = _name;
		}
		ServiceManifest m = _manifestMap.get(name);
		if (m == null) {
			m = new ServiceManifest();
			m.buildId = "no-manifest-found";
		}
		return m;
	}
	
}
