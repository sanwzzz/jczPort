package handler;

import cn.hutool.log.level.WarnLog;
import communication.Model.ChannelModel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class DefaultListenerHanler extends ChannelInboundHandlerAdapter {


	private Channel remoteChannel = ChannelModel.getInstance().getForwardChannel();
	private Channel localChannel;


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		this.localChannel = ctx.channel();
		ChannelModel.getInstance().setListenerChannel(this.localChannel);
		log.info("listener actice success");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		super.channelRead(ctx, msg);
		if (Objects.isNull(remoteChannel))
			this.remoteChannel = ChannelModel.getInstance().getListenerChannel();
		if (Objects.nonNull(remoteChannel))
			this.remoteChannel.writeAndFlush(msg);
		else
			log.warn("listenet read failed，remoteChannel is null!");
	}


}
