package br.com.hsi.util.mail;

import com.outjected.email.api.EmailContact;

public class SimpleEmailConcat implements EmailContact{

	
	private String name;
	private String address;

	
	public SimpleEmailConcat(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}

	@Override
	public String getName() {
		
		return name;
	}

	@Override
	public String getAddress() {

		return address;
	}

}
