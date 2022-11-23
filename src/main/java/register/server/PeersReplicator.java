package register.server;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PeersReplicator {

    private static final long PEERS_REPLICATE_BATCH_INTERVAL = 500;

    private static final PeersReplicator instance = new PeersReplicator();
    private PeersReplicator(){
        AcceptorBatchThread thread = new AcceptorBatchThread();
        thread.setDaemon(true);
        thread.start();

        PeersReplicateThread peersReplicateThread = new PeersReplicateThread();
        peersReplicateThread.setDaemon(true);
        peersReplicateThread.start();
    }

    public static PeersReplicator getInstance() {
        return instance;
    }

    public void replicateRegister(RegisterRequest request) {
        acceptorQueue.offer(request);
    }

    public void replicateCancel(CancelRequest request) {
        acceptorQueue.offer(request);
    }

    public void replicateHeartbeat(HeartbeatRequest request) {
        acceptorQueue.offer(request);
    }

    /**
     * 第一层队列无界队列
     */
    private ConcurrentLinkedQueue<Request> acceptorQueue = new ConcurrentLinkedQueue<Request>();

    /**
     * 第二层队列有界队列，用于batch的生成
     */
    private LinkedBlockingQueue<Request> batchQueue = new LinkedBlockingQueue<Request>(1000);

    /**
     * 第三层队列有界队列，用于batch的同步发送
     */
    private LinkedBlockingQueue<PeersReplicateBatch> replicateQueue = new LinkedBlockingQueue<PeersReplicateBatch>(1000);


    class PeersReplicateThread extends Thread {

        @Override
        public void run() {
            while(true) {
                try {
                    PeersReplicateBatch batch = replicateQueue.take();
                    if (batch != null) {
                        System.out.println("send http request for sync...");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class AcceptorBatchThread extends Thread {

        long latestBatchGeneration = System.currentTimeMillis();

        @Override
        public void run() {
            while (true) {
                try {
                    Request request = acceptorQueue.poll();
                    batchQueue.put(request);

                    long now = System.currentTimeMillis();

                    if (now - latestBatchGeneration >= PEERS_REPLICATE_BATCH_INTERVAL) {
                        if (batchQueue.size() > 0) {

                        }
                        this.latestBatchGeneration = System.currentTimeMillis();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private PeersReplicateBatch createBatch() {
            PeersReplicateBatch batch = new PeersReplicateBatch();
            Iterator<Request> iterator = batchQueue.iterator();
            while(iterator.hasNext()) {
                Request request = iterator.next();
                batch.add(request);
            }
            batchQueue.clear();
            return batch;
        }
    }



}
