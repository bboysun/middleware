## 中间件相关的学习

在这里，我们即将学习kafka, zookeeper, redis, thread等相关的知识。

#### 多线程并发

今天，我们首先学习一下多线程并发的问题，简单的模拟一个多个线程假死的场景。我们需要了解到只有当多个线程任务共同争抢同一个资源的时候就产生死锁的现象，或者假死的现象。

栗子中，生产火箭和消费火箭，我们期望的是不停的循环生产完消费，消费完再生产的结果，但是在我们的栗子中，会发现执行的某一时刻就就hang死不会动了，这是为什么呢？

仔细想想，是因为我们每次notify()的时候，有可能会唤醒同一种类型的类（生产者唤醒生产者，消费者唤醒消费者），假死现象就是全部线程都进入了WAITING状态，此时程序不再执行任何功能。

当发生了这样的问题时，我们可以通过jstack + pid来查看是否有假死的现象发生。那我们怎么解决这个问题呢？

我们要用notifyAll()的方法去唤醒所有的等待线程去执行相关任务，这样避免了只唤醒同类型的对象类。