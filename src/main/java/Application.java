import communication.forward.ForwardStarter;
import communication.listener.ListenerStarter;
import config.Configuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {


	public static void main(String[] args) throws Exception {
		Configuration config = new Configuration();
		config.init();

		ListenerStarter listenerAdapter = new ListenerStarter(config);
		listenerAdapter.start();

		ForwardStarter forwardStarter = new ForwardStarter(config);
		forwardStarter.start();
		log.info("启动完成");
	}
}
