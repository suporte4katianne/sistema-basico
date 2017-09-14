package br.com.hsi.util.mail;

import com.outjected.email.api.ContentDisposition;
import com.outjected.email.api.EmailAttachment;
import com.outjected.email.api.Header;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class SimpleEmailAttachment implements EmailAttachment {

	private String fileName;
	private byte[] bytes;
	private Collection<Header> headers = new ArrayList<>();

	public SimpleEmailAttachment(String fileName, File file) throws IOException {
		super();		
		this.bytes = FileUtils.readFileToByteArray(file);
		this.fileName = fileName;
		headers.add(new Header(fileName, fileName));	
	}

	@Override
	public ContentDisposition getContentDisposition() {
		return ContentDisposition.ATTACHMENT;
	}

	@Override
	public String getContentId() {
		return null;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public String getMimeType() {
		return null;
	}


	@Override
	public Collection<Header> getHeaders() {
		return headers;
	}

	@Override
	public byte[] getBytes() {
		return bytes;
	}

}
