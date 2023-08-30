package communication.forward;

import communication.Machine;
import config.Configuration;
import forward.Forward;
import lombok.extern.slf4j.Slf4j;

@Slf4j//转发器
public class ForwardStarter extends Machine {

	private Forward forward;
	public ForwardStarter(Configuration config) {
		forward = new Forward(config);
	}

	@Override
	protected void startInternal() throws Exception {
		Thread thread = new Thread(forward,"MainForwardClient");
		thread.start();
	}

	@Override
	protected void shutdownInternal() throws Exception {

	}

}
