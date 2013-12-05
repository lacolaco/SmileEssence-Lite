package net.miz_hi.smileessence.task;

import net.miz_hi.smileessence.core.MyExecutor;
import net.miz_hi.smileessence.util.UiHandler;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public abstract class Task<T> implements Callable<T>
{

    protected Runnable callback;

    public Task<T> setCallBack(Runnable callback)
    {
        this.callback = callback;
        return this;
    }

    public Future<T> callAsync()
    {
        onPreExecute();
        final Future<T> future = MyExecutor.submit(this);
        MyExecutor.execute(new Runnable()
        {

            @Override
            public void run()
            {

                try
                {
                    final T result = future.get();
                    new UiHandler()
                    {

                        @Override
                        public void run()
                        {
                            onPostExecute(result);
                            if (callback != null)
                            {
                                callback.run();
                            }
                        }
                    }.post();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                catch (ExecutionException e)
                {
                    e.printStackTrace();
                }
            }
        });
        return future;
    }

    public abstract void onPreExecute();

    /**
     * this is called on Ui Thread
     *
     * @param result: result of task
     */
    public abstract void onPostExecute(T result);

}
