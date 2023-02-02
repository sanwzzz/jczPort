package communication;

public abstract class Meth{



	public void start() throws Exception {
		startInternal();
	}
	/**
	 *
	 *
	 * @throws Exception
	 */
	protected abstract void startInternal() throws Exception;

	/**
	 *
	 * @throws Exception
	 */
	protected abstract void shutdownInternal() throws Exception;
}
