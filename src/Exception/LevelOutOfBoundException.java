package Exception;

public class LevelOutOfBoundException extends Exception{
    String msg = "Exception: ";
    public LevelOutOfBoundException(String str) {
        msg += str;
    }

    @Override
    public String toString() {
        return msg;
    }
}
