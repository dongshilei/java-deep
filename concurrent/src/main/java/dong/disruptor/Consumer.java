package dong.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * Created by DONGSHILEI on 2017/6/29.
 */
public class Consumer implements WorkHandler<TaskData> {
    @Override
    public void onEvent(TaskData taskData) throws Exception {
        System.out.println(Thread.currentThread().getId()+" Event:--"+taskData.getValue()*taskData.getValue());
    }
}
