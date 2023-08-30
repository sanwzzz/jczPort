package communication.listener;

import communication.Machine;
import config.Configuration;
import handler.DefaultListenerHanler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import listener.Listener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListenerStarter extends Machine {
	private Listener listener;
	public ListenerStarter(Configuration config) {
		listener = new Listener(config);
	}

	@Override
	protected void startInternal() throws Exception {
		Thread thread = new Thread(listener,"MainListenerServer");
		thread.start();
	}

	@Override
	protected void shutdownInternal() throws Exception {
	}

}
