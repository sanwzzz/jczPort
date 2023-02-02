import communication.Meth;
import communication.forward.ForwardAdapter;
import communication.listener.ListenerAdapter;
import config.Configuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {


	public static void main(String[] args) throws Exception {
		Configuration config = new Configuration();
		config.init();

		ListenerAdapter listenerAdapter = new ListenerAdapter(config);
		listenerAdapter.start();
		ForwardAdapter forwardAdapter = new ForwardAdapter(config);
		forwardAdapter.start();
		log.info("启动完成");
	}
}
