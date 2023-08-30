package config;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Data;
import util.JUtil;

@Data
public class Configuration {


	private int listenerPort = 28555;

	private String forwardHost = "127.0.0.1";

	private int fowwardPort = 28556;

	private boolean useEpoll = false;

	private int bossThreads = 1;
	private int workerThreads = 1;



	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;


	public void setUseEpoll(boolean useEpoll) {
		this.useEpoll = useEpoll && JUtil.platformIsLinux();
	}

	public void init(){
		bossGroup = this.useEpoll ? new EpollEventLoopGroup(this.bossThreads) : new NioEventLoopGroup(this.bossThreads);
		workerGroup = this.useEpoll ? new EpollEventLoopGroup(this.workerThreads) : new NioEventLoopGroup(this.workerThreads);
	}


}
