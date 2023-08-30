package config;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Data;
import utils.JUtil;

@Data
public class Configural {


    //Listener port default 28555
    private int listenerPort = 28555;
    // default false
    private boolean useEpoll = false;
    // default 1
    private int bossThreads = 1;
    // default 4
    private int workThreads = 4;

    public void setUseEpoll(boolean useEpoll) {
        this.useEpoll = useEpoll && JUtil.isLinux();
    }


    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    public void init(){
        bossGroup = this.isUseEpoll() ? new NioEventLoopGroup(this.getBossThreads()) : new EpollEventLoopGroup(this.getBossThreads());
        workerGroup = this.isUseEpoll() ? new NioEventLoopGroup(this.getWorkThreads()) : new EpollEventLoopGroup(this.getWorkThreads());
    }


}
