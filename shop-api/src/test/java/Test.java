import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.netease.checksum.CheckSumBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;

public class Test {
    //
    public static void main(String[] args) {
        String a = new String("1");//String和封装类重写过equals 会根据值删 正常会根据内存地址删
        String a2 = new String("1");
        String a3 = new String("1");
        String a4 = new String("1");
        ArrayList<String> l = new ArrayList<>();
        l.add(a);
        l.add(a2);
        l.add(a3);
        l.add(a4);
        ArrayList<String> l2 = new ArrayList<>();
        l2.add(a);
        l2.add(a2);
        l.removeAll(l2);
        //a4.append("---");
        l2.add(a4);
        System.out.println(l.size());


    }

    @org.junit.Test
    public void test() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("192.168.214.128", 30001);
        System.out.println("连接成功");

        //
        jedis.set("ceshi", "111");
        System.out.println(jedis.get("ceshi"));

        //查看服务是否运行
        System.out.println("服务正在运行: " + jedis.ping());
    }

    @org.junit.Test
    public void tests() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大连接数
        poolConfig.setMaxTotal(1);
        // 最大空闲数
        poolConfig.setMaxIdle(1);
        // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
        // Could not get a resource from the pool
        poolConfig.setMaxWaitMillis(1000);
        Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.214.128", 30001));
        nodes.add(new HostAndPort("192.168.214.128", 30002));
        nodes.add(new HostAndPort("192.168.214.128", 30003));
        nodes.add(new HostAndPort("192.168.214.128", 30004));
        nodes.add(new HostAndPort("192.168.214.128", 30005));
        nodes.add(new HostAndPort("192.168.214.128", 30006));
        JedisCluster cluster = new JedisCluster(nodes, poolConfig);
        String name = cluster.get("name");
        System.out.println(name);
        cluster.set("age", "18");
        System.out.println(cluster.get("age"));
        try {
            cluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * oss 文件上传
     */
    @org.junit.Test
    public void test2() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4FnKceUxLg8iPC7VeV2b";
        String accessKeySecret = "DczuwiuWWq4Nv5Uu0FbKkIW7UkBNwG";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
        ossClient.putObject("aly-oss-ggh-test", "images/" + UUID.randomUUID().toString(), new File("D:/download/2c9ca593f4b3c48e32bfbd092a56c8d3.jpeg"));

        // 关闭OSSClient。
        ossClient.shutdown();
        System.out.println();
    }

    /**
     * oss图片私有权限展示
     */
    @org.junit.Test
    public void test3() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4FnKceUxLg8iPC7VeV2b";
        String accessKeySecret = "DczuwiuWWq4Nv5Uu0FbKkIW7UkBNwG";
        String bucketName = "aly-oss-ggh-test";
        String objectName = "images/009c0791-0dce-4c27-9744-fd229d63f6e1.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 设置URL过期时间为1小时。
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * httpClient
     */
    @org.junit.Test
    public void test4() {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("http://localhost:8086/brands/11");

        try {
            CloseableHttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * httpClient 网易云信
     */
    @org.junit.Test
    public void test5() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {

            HttpPost post = new HttpPost("https://api.netease.im/sms/sendcode.action");

            post.addHeader("AppKey", "854154723a80cdf85fc757801c477c82");
            String nonce = UUID.randomUUID().toString();
            post.addHeader("Nonce", nonce);
            String curTime = System.currentTimeMillis() + "";
            String appSecret = "11ed9411d835";
            post.addHeader("CurTime", curTime);
            post.addHeader("CheckSum", CheckSumBuilder.getCheckSum(appSecret, nonce, curTime));

            List list = new ArrayList();
            BasicNameValuePair mobilePair = new BasicNameValuePair("mobile", "15910933097");
            list.add(mobilePair);
            UrlEncodedFormEntity urlEncodedFormEntity = null;
            urlEncodedFormEntity = new UrlEncodedFormEntity(list, "utf-8");
            post.setEntity(urlEncodedFormEntity);

            CloseableHttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }

    }
}
