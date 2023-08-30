package forward;

import config.Configuration;
import handler.DefaultFowardHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Forward implements Runnable{

	private final Configuration config;
	public Forward(Configuration config) {
		this.config = config;
	}


	private Bootstrap bootstrap;


	public void init() {
		bootstrap = new Bootstrap();
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.group(config.getWorkerGroup());
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
			protected void initChannel(NioSocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(new DefaultFowardHandler());
			}
		});

		bootstrap.remoteAddress(config.getForwardHost(), config.getFowwardPort());


		connect();
	}


	private volatile Channel channel;

	public Channel getChannel() {
		return channel;
	}

	public void connect(){
		ChannelFuture connect = bootstrap.connect();
		connect.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()){
					log.info("server connect remote success");
				}else {
					log.warn("server connect remote fail host:{}, port:{}; 1 sec try again", config.getForwardHost(), config.getFowwardPort());
					future.channel().eventLoop().schedule(()-> connect(), 1, TimeUnit.SECONDS);
				}
			}
		});
	}

	@Override
	public void run() {
		init();
	}
}
