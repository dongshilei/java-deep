package dong.forkJoin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by DONGSHILEI on 2017/6/23.
 */
public class CrawlerTask extends RecursiveTask {
    private static final int THRESHOLD= 100;//阈值 作为任务切分的标准
    private int start;
    private int end;

    public CrawlerTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Object compute() {
        int sum = 0;
        boolean camCompute = (end-start)<=THRESHOLD;
        if (camCompute){
            System.out.println("执行任务*******start:"+start+"  end:"+end);
            Document doc = null;
            for (int i=start;i<=end;i++) {
                String url = "http://www.ruyile.com/xuexiao/?t=1&p=" + (i + 1);
                try {
                    doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36").timeout(30000).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Iterator<Element> ir = doc.select(".sk").iterator(); ir.hasNext(); ) {
                    Element element = ir.next();
                    //System.out.println(Thread.currentThread().getName()+"====="+element.text());
                    sum+=1;
                    /*List<TextNode> textNodes = element.textNodes();
                    for (TextNode tn : textNodes) {
                        sum +=1;
                        //System.out.printf(tn.text()+" ");
                    }*/
                    //System.out.printf("");
                }
            }
        } else {
            System.out.println("分割任务*******");
            int middle = (start+end)/2;
            CrawlerTask lt = new CrawlerTask(start,middle);
            CrawlerTask rt = new CrawlerTask(middle+1,end);
            lt.fork();
            rt.fork();
            sum=(int)lt.join()+(int)rt.join();
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CrawlerTask crawlerTask = new CrawlerTask(1,500);
        ForkJoinTask forkJoinTask = forkJoinPool.submit(crawlerTask);
        System.out.printf(String.valueOf(forkJoinTask.get()));
    }
}
