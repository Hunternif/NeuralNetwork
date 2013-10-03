package hunternif.nn;

public class NNException extends Exception {
	private static final long serialVersionUID = -291261156668726548L;
	
	public NNException(String message) {
		super(message);
	}
	
	public NNException(Throwable cause) {
		super(cause);
	}
	
	public NNException(String message, Throwable cause) {
		super(message, cause);
	}
}
