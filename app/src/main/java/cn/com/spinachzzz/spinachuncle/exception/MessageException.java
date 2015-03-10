package cn.com.spinachzzz.spinachuncle.exception;

public class MessageException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MessageException() {
	super();

    }

    public MessageException(String detailMessage, Throwable throwable) {
	super(detailMessage, throwable);

    }

    public MessageException(String detailMessage) {
	super(detailMessage);

    }

    public MessageException(Throwable throwable) {
	super(throwable);

    }

}
