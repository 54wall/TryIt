package pri.weiqiang.tryit.shell;

/**
 * 通过回调，返回结果后，在调用ShellExecutor显示结果
 *
 * @author weiqiang
 */
public interface OnResultListener {
    void OnResult(ShellExecutor executor);

}
