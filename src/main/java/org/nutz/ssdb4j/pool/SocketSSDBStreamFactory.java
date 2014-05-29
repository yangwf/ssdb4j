package org.nutz.ssdb4j.pool;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.nutz.ssdb4j.impl.SocketSSDBStream;
import org.nutz.ssdb4j.spi.Cmd;
import org.nutz.ssdb4j.spi.SSDBStream;

public class SocketSSDBStreamFactory extends BasePoolableObjectFactory<SSDBStream> {

	protected String host;
	protected int port;
	protected int timeout;
	
	public SocketSSDBStreamFactory(String host, int port, int timeout) {
		this.host = host;
		this.port = port;
		this.timeout = timeout;
	}
	@Override
	public SSDBStream makeObject() throws Exception {
		return new SocketSSDBStream(host, port, timeout);
	}
	@Override
	public boolean validateObject(SSDBStream stream) {
		try {
			return stream.req(Cmd.ping).ok();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void destroyObject(SSDBStream obj) throws Exception {
		obj.close();
	}
}
