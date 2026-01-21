package kr.astar.uliv.exception;

public class InvalidApiKeyException extends RuntimeException {
    public InvalidApiKeyException() {
        super("Invalid API Key");
    }
}