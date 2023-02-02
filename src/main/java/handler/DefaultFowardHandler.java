package handler;

import communication.Model.ChannelModel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class DefaultFowardHandler extends ChannelInboundHandlerAdapter {


	public Channel remoteChannel = ChannelModel.getInstance().getListenerChannel();
	public Channel localChanel;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		this.localChanel = ctx.channel();
		ChannelModel.getInstance().setForwardChannel(this.localChanel);
		log.info("forward accept success");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		log.warn("remote service disconnect link");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		super.channelRead(ctx, msg);
		if (Objects.isNull(remoteChannel))
			this.remoteChannel = ChannelModel.getInstance().getListenerChannel();
		if (Objects.nonNull(remoteChannel))
			this.remoteChannel.writeAndFlush(msg);
		else
			log.warn("foward read failed，remoteChannel is null!");
	}
}
