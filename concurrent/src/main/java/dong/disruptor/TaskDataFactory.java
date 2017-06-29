package dong.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 工厂类作用：在Disruptor系统初始化时，构造所有的缓冲区中的对象实例（预先分配空间）
 * Created by DONGSHILEI on 2017/6/29.
 */
public class TaskDataFactory implements EventFactory<TaskData> {
    @Override
    public TaskData newInstance() {
        return new TaskData();
    }
}
