package communication.listener;

import communication.Meth;
import communication.Model.ChannelModel;
import config.Configuration;
import handler.DefaultListenerHanler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListenerAdapter extends Meth {
	private Listener listener;
	public ListenerAdapter(Configuration config) {
		listener = new Listener(config);
	}

	@Override
	protected void startInternal() throws Exception {
		Thread thread = new Thread(listener,"ListenerServerT");
		thread.start();
	}

	@Override
	protected void shutdownInternal() throws Exception {
	}

	private class Listener implements Runnable{

		private Configuration config;

		public Listener(Configuration config) {
			this.config = config;
		}

		private ServerBootstrap bootstrap;
		private volatile Channel channel;
		//通信
		public void init(){
			bootstrap = new ServerBootstrap();
			bootstrap.group(config.getBossGroup(), config.getWorkerGroup());
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
			bootstrap.option(ChannelOption.TCP_NODELAY, true);
			bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3);
			bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
				protected void initChannel(NioSocketChannel channel) throws Exception {
					ChannelPipeline pipeline = channel.pipeline();
					pipeline.addLast(new DefaultListenerHanler());
				}
			});
			try {
				ChannelFuture bindFuture = bootstrap.bind(config.getListenerPort());
				bindFuture.sync().addListener(new ChannelFutureListener() {
					public void operationComplete(ChannelFuture future) throws Exception {
						if (future.isSuccess()){
							log.info("listener server starter. port: {}", config.getListenerPort());
						}else {
							log.warn("listener server start failed. port: {}", config.getListenerPort());
						}
					}
				});

				bindFuture.channel().closeFuture().sync().addListener(new ChannelFutureListener() {
					public void operationComplete(ChannelFuture future) throws Exception {
						log.info("listener server close. port: {}");
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void run() {
			init();
		}
	}
}
