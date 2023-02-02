package communication.Model;

import io.netty.channel.Channel;

public class ChannelModel {


	private static ChannelModel channelModel = new ChannelModel();

	public static ChannelModel getInstance() {
	    return channelModel;
	}

	private volatile Channel listenerChannel;
	private volatile Channel forwardChannel;


	public Channel getListenerChannel() {
		return listenerChannel;
	}

	public void setListenerChannel(Channel listenerChannel) {
		this.listenerChannel = listenerChannel;
	}

	public Channel getForwardChannel() {
		return forwardChannel;
	}

	public void setForwardChannel(Channel forwardChannel) {
		this.forwardChannel = forwardChannel;
	}
}
