package vo;

public class Result
{
    public static final int SUCCESS = 1;
    public static final int ERROR = 0;

    private Object data;

    private Integer code;

    private String message;

    private Result(Integer code)
    {
        this.code = code;
    }

    public Result() {
        super();
        // TODO Auto-generated constructor stub
    }


    private static Result create(Integer code) {
        return new Result(code);
    }

    public static Result createSuccessResult() {
        return create(Integer.valueOf(1));
    }

    public static Result createErrorResult(Integer code, String message) {
        return create(code).setMessage(message);
    }

    public Object getData() {
        return this.data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return this.code;
    }

    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }
}
