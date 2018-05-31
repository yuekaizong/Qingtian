package kaizones.songmaya.qingtian.test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

public class CommandHelloWorld3 extends HystrixCommand<String> {
    private final int id;

    protected CommandHelloWorld3(int id) {
        super(HystrixCommandGroupKey.Factory.asKey("RequestCacheCommand"));
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        System.out.println(Thread.currentThread().getName() + " execute id = " + id);
        return "execute=" + id;
    }

    //重写getCacheKey，实现区分不同请求的逻辑
    @Override
    protected String getCacheKey() {
        System.out.println(" --- ");
        return String.valueOf(id);
    }

    public static void main(String[] args) {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        try {
            CommandHelloWorld3 commandHelloWorld3_a = new CommandHelloWorld3(22);
            CommandHelloWorld3 commandHelloWorld3_b = new CommandHelloWorld3(22);

            System.out.println("a执行结果:" + commandHelloWorld3_a.execute());
            System.out.println("a执行结果是否从缓存中获取：" + commandHelloWorld3_a.isResponseFromCache);

            System.out.println("b执行结果:" + commandHelloWorld3_b.execute());
            System.out.println("b执行结果是否从缓存中获取：" + commandHelloWorld3_b.isResponseFromCache);
        } finally {
            context.shutdown();
        }

        context = HystrixRequestContext.initializeContext();
        try {
            CommandHelloWorld3 commandHelloWorld3_c = new CommandHelloWorld3(22);

            System.out.println("c执行结果:" + commandHelloWorld3_c.execute());
            System.out.println("c执行结果是否从缓存中获取：" + commandHelloWorld3_c.isResponseFromCache);

        } finally {
            context.shutdown();
        }

        /**
         * 注意:
         * 请求缓存可以让(CommandKey/CommandGroup)相同的情况下,
         * 直接共享结果，降低依赖调用次数，
         * 在高并发和CacheKey碰撞率高场景下可以提升性能.
         */
    }
}
