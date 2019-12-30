package com.bluersw.jenkins.shared.library

class Node {

	String key;
	String value;
	List<Node> childNodes;
	Node parentNode;

	public Node(String key, String value){
		this.key = key
		this.value = value
	}
}
