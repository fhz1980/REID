package com.ffait.reid;

public class TraingResult {
    String name;
    String fzk;
    String completeProject;
    String uncompleteProject;
    boolean isUncomplete=true;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFzk() {
		return fzk;
	}
	public void setFzk(String fzk) {
		this.fzk = fzk;
	}
	public String getCompleteProject() {
		return completeProject;
	}
	public void setCompleteProject(String completeProject) {
		this.completeProject = completeProject;
	}
	public String getUncompleteProject() {
		return uncompleteProject;
	}
	public void setUncompleteProject(String uncompleteProject) {
		this.uncompleteProject = uncompleteProject;
	}
	public boolean isUncomplete() {
		return isUncomplete;
	}
	public void setUncomplete(boolean isUncomplete) {
		this.isUncomplete = isUncomplete;
	}
    
}
