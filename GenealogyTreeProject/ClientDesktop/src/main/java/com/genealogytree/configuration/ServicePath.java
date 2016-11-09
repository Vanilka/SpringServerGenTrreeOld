package com.genealogytree.configuration;

public enum ServicePath {
	USER ("user"),
	FAMILY("family");
	
    private String path;
    private ServicePath(String path) {
        this.path = path;
    }
    
    @Override
    public String toString() {
        return path;
    }
}
