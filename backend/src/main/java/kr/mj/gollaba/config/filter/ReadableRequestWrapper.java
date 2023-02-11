package kr.mj.gollaba.config.filter;

import com.querydsl.core.util.StringUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ReadableRequestWrapper extends HttpServletRequestWrapper {
	private final Charset encoding;
	private byte[] rawData;
	
	public ReadableRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		String charEncoding = request.getCharacterEncoding();
		this.encoding = StringUtils.isNullOrEmpty(charEncoding) ? StandardCharsets.UTF_8 : Charset.forName(charEncoding);
		
		try {
			InputStream is = request.getInputStream();
			this.rawData = IOUtils.toByteArray(is);
		} catch (IOException e) {
			throw e;
		}
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);
		ServletInputStream servletInputStream = new ServletInputStream() {
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setReadListener(ReadListener listener) {
				// TODO Auto-generated method stub
				
			}
		};
		return servletInputStream;
	}
	
	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
	}
}
