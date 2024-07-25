package com.lcyy.stock.service.impl;

import com.google.common.collect.Lists;
import com.lcyy.stock.config.StockTaskRunable;
import com.lcyy.stock.face.StockCacheFace;
import com.lcyy.stock.mapper.*;
import com.lcyy.stock.pojo.entity.StockBlockRtInfo;
import com.lcyy.stock.pojo.entity.StockMarketIndexInfo;
import com.lcyy.stock.pojo.entity.StockOuterMarketIndexInfo;
import com.lcyy.stock.pojo.entity.StockRtInfo;
import com.lcyy.stock.pojo.vo.StockInfoConfig;
import com.lcyy.stock.service.StockTimerTaskService;
import com.lcyy.stock.utils.DateTimeUtil;
import com.lcyy.stock.utils.IdWorker;
import com.lcyy.stock.utils.ParseType;
import com.lcyy.stock.utils.ParserStockInfoUtil;
import com.sun.javafx.fxml.ParseTraceElement;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.joda.time.DateTime;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.security.mscapi.CPublicKey;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * @author: dlwlrma
 * @data 2024年06月30日 13:44
 * @Description: TODO: A股大盘数据采集
 */
@Service("StockTimerTaskService")
@Slf4j
public class StockTimerTaskServiceImpl implements StockTimerTaskService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    //入库
    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private ParserStockInfoUtil parserStockInfoUtil;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private HttpEntity httpEntity;

    @Autowired
    private StockOuterMarketIndexInfoMapper stockOuterMarketIndexInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private StockCacheFace stockCacheFace;

    @Override
    public void getInnerMarketInfo() {
//        //1.采集原始数据
//        //1.1组装url
//        String url = stockInfoConfig.getMarketUrl() + String.join(",", stockInfoConfig.getInner());
//        //1.2维护请求头
//        HttpHeaders headers = new HttpHeaders();
//        //防盗链
//        headers.add("Referer", "https://finance.sina.com.cn/stock/");
//        //添加用户标识
//        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
//        //维护http请求实体对象
//        HttpEntity<Object> entity = new HttpEntity<>(headers);
//        //发起请求
//        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//        if (responseEntity.getStatusCodeValue() != 200) {
//            //当前请求失败
//            log.error("当前时间点：{}，采集数据失败，http状态码：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), responseEntity.getStatusCodeValue());
//            //通知相关人员
//            return;
//        }
//        //获取js数据
//        String body = responseEntity.getBody();
//        log.info("当前时间点：{},采集到的原始数据：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), body);
//
//        //2.1解析采集到的数据定义正则表达式
//        String reg = "var hq_str_(.+)=\"(.+)\";";
//        //2.2表达式编译
//        Pattern pattern = Pattern.compile(reg);
//        //匹配字符串
//        Matcher matcher = pattern.matcher(body);
//        List<StockMarketIndexInfo> entities = new ArrayList<>();
//        while (matcher.find()) {
//            //1.获取大盘编码
//            String marketCode = matcher.group(1);
//            //2.获取其他信息
//            String otherInfo = matcher.group(2);
//            //以逗号切割字符串，形成数组
//            String[] splitArr = otherInfo.split(",");
//            //大盘名称
//            String marketName = splitArr[0];
//            //获取当前大盘的开盘点数
//            BigDecimal openPoint = new BigDecimal(splitArr[1]);
//            //前收盘点
//            BigDecimal preClosePoint = new BigDecimal(splitArr[2]);
//            //获取大盘的当前点数
//            BigDecimal curPoint = new BigDecimal(splitArr[3]);
//            //获取大盘最高点
//            BigDecimal maxPoint = new BigDecimal(splitArr[4]);
//            //获取大盘的最低点
//            BigDecimal minPoint = new BigDecimal(splitArr[5]);
//            //获取成交量
//            Long tradeAmt = Long.valueOf(splitArr[8]);
//            //获取成交金额
//            BigDecimal tradeVol = new BigDecimal(splitArr[9]);
//            //时间
//            Date curTime = DateTimeUtil.getDateTimeWithoutSecond(splitArr[30] + " " + splitArr[31]).toDate();
//            //3.解析到的数据封装entity
//            StockMarketIndexInfo entitys = StockMarketIndexInfo.builder()
//                    .id(idWorker.nextId())
//                    .marketName(marketName)
//                    .openPoint(openPoint)
//                    .preClosePoint(preClosePoint)
//                    .curPoint(curPoint)
//                    .maxPoint(maxPoint)
//                    .minPoint(minPoint)
//                    .tradeAmount(tradeAmt)
//                    .tradeVolume(tradeVol)
//                    .marketCode(marketCode)
//                    .curTime(curTime)
//                    .build();
//            entities.add(entitys);
//        }
//        log.info("解析大盘数据完成");
//
//        //4.调用mybatis批量入库
//        int count = stockMarketIndexInfoMapper.insertBatch(entities);
//        if (count > 0) {
//            //大盘数据采集完毕后通知backed工程刷新入库
//            //发送日期时间，接收方通过时间判断时间是否超时，能判断时间的延迟
//            rabbitTemplate.convertAndSend("stockExchange", "Inner.Market", new Date());
//            log.info("当前时间：{},{}插入大盘数据成功", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), entities);
//        } else {
//            log.info("当亲时间：{}，{}插入大盘数据失败", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), entities);
//        }

        String url = stockInfoConfig.getMarketUrl() + String.join(",", stockInfoConfig.getInner());
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        if (statusCodeValue != 200) {
            log.error("采集国内大盘数据失败,状态码：{}", statusCodeValue);
        }
        String jsData = responseEntity.getBody();
        List<StockMarketIndexInfo> entities = parserStockInfoUtil.parser4StockOrMarketInfo(jsData,ParseType.INNER);
        log.info("采集的当前国内大盘数据：{}", entities);
        int count = stockMarketIndexInfoMapper.insertBatch(entities);
        if (count > 0) {
            rabbitTemplate.convertAndSend("stockExchange","inner.market",new Date());
            log.info("插入国内大盘数据成功！批量插入了：{}条数据", count);
        } else {
            log.info("插入国内大盘数据失败！");
        }
    }

    @Override
    public void getStockRtIndex() {
        //获取所有的个股结合
        //使用springcache缓存方案
        List<String> allStockCodes = stockCacheFace.getAllStockCodeWithPredix();
//        List<String> allStockCodes = stockBusinessMapper.getAllStockCodes();
//        //收集到的编码没有前缀编码，因此转换为有前缀编码的
//        allStockCodes = allStockCodes.stream().map(code -> code.startsWith("6") ? "sh" + code : "sz" + code).collect(Collectors.toList());
        //将所有的大盘编码集合拆分为小的编码集合（分组45->15,15,10）
        //使用的谷歌 guava 工具包，可以将一个很大的集合拆分成若干的小的集合
        Lists.partition(allStockCodes, 15).forEach(codes -> {
            //每次来任务，就需要创建一个线程，复用性差；cpu的竞争过于激烈，导致频繁的上下文切换，性能降低
            //原始方案采集个股数据数据分片，然后分批次进行采集效率不高，存在较高的数据延迟，引入多线程
//            //分批次采集,获取到的url地址
//            String url = stockInfoConfig.getMarketUrl() + String.join(",", codes);
//            HttpHeaders headers = new HttpHeaders();
//            //防盗链
//            headers.add("Referer","https://finance.sina.com.cn/stock/");
//            //添加用户标识
//            headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
//            //维护http请求实体对象
//            HttpEntity<Object> entity = new HttpEntity<>(headers);
//            //发起请求
//            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//            if (responseEntity.getStatusCodeValue()!=200) {
//                //当前请求失败
//                log.error("当前时间点：{}，采集数据失败，http状态码：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),responseEntity.getStatusCodeValue());
//                //通知相关人员
//                return;
//            }
//            //获取js数据
//            String body = responseEntity.getBody();
//            //解析js，调用工具类
//            List<StockRtInfo> list =  parserStockInfoUtil.parser4StockOrMarketInfo(body, ParseType.ASHARE);
//            log.info("当前时间：{},{}成功",DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),list);
//
//            //批量插入
//            int count = stockRtInfoMapper.insertBatch(list);
//            if (count>0) {
//                log.info("当前时间：{},{}插入个股数据成功",DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),list);
//            }else {
//                log.info("当亲时间：{}，{}插入个股数据失败",DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),list);
//            }
            new Thread(() -> {
//                //分批次采集,获取到的url地址
//            String url = stockInfoConfig.getMarketUrl() + String.join(",", codes);
//            HttpHeaders headers = new HttpHeaders();
//            //防盗链
//            headers.add("Referer","https://finance.sina.com.cn/stock/");
//            //添加用户标识
//            headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
//            //维护http请求实体对象
//            HttpEntity<Object> entity = new HttpEntity<>(headers);
//            //发起请求
//            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//            if (responseEntity.getStatusCodeValue()!=200) {
//                //当前请求失败
//                log.error("当前时间点：{}，采集数据失败，http状态码：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),responseEntity.getStatusCodeValue());
//                //通知相关人员
//                return;
//            }
//            //获取js数据
//            String body = responseEntity.getBody();
//            //解析js，调用工具类
//            List<StockRtInfo> list =  parserStockInfoUtil.parser4StockOrMarketInfo(body, ParseType.ASHARE);
//            log.info("当前时间：{},{}成功",DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),list);
//
//            //批量插入
//            int count = stockRtInfoMapper.insertBatch(list);
//            if (count>0) {
//                log.info("当前时间：{},{}插入个股数据成功",DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),list);
//            }else {
//                log.info("当亲时间：{}，{}插入个股数据失败", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), list);
//            }
//            }).start();
                //方案2，引入线程池
                threadPoolTaskExecutor.execute(() -> {
                    //                //分批次采集,获取到的url地址
                    String url = stockInfoConfig.getMarketUrl() + String.join(",", codes);
                    HttpHeaders headers = new HttpHeaders();
                    //防盗链
                    headers.add("Referer", "https://finance.sina.com.cn/stock/");
                    //添加用户标识
                    headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
                    //维护http请求实体对象
                    HttpEntity<Object> entity = new HttpEntity<>(headers);
                    //发起请求
                    ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                    if (responseEntity.getStatusCodeValue() != 200) {
                        //当前请求失败
                        log.error("当前时间点：{}，采集数据失败，http状态码：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), responseEntity.getStatusCodeValue());
                        //通知相关人员
                        return;
                    }
                    //获取js数据
                    String body = responseEntity.getBody();
                    //解析js，调用工具类
                    List<StockRtInfo> list = parserStockInfoUtil.parser4StockOrMarketInfo(body, ParseType.ASHARE);
                    log.info("当前时间：{},{}成功", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), list);

                    //批量插入
                    int count = stockRtInfoMapper.insertBatch(list);
                    if (count > 0) {
                        log.info("当前时间：{},{}插入个股数据成功", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), list);
                    } else {
                        log.info("当亲时间：{}，{}插入个股数据失败", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), list);
                    }
                });
            });
        });
    }

    @Override
    public void getOuterMarketInfo() {
        String url = stockInfoConfig.getMarketUrl() + String.join(",", stockInfoConfig.getOuter());
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        if (statusCodeValue != 200) {
            log.error("采集国外大盘数据失败,状态码：{}", statusCodeValue);
        }
        String jsData = responseEntity.getBody();
        List<StockOuterMarketIndexInfo> entities = parserStockInfoUtil.parser4StockOrMarketInfo(jsData, ParseType.OUTER);
        log.info("采集的当前国外大盘数据：{}", entities);
        int count = stockOuterMarketIndexInfoMapper.insertBatch(entities);
        if (count > 0) {
            rabbitTemplate.convertAndSend("stockExchange", "outer.market", new Date());
            log.info("插入国外大盘数据成功！批量插入了：{}条数据", count);
        } else {
            log.info("插入国外大盘数据失败！");
        }
    }

    @Override
    public void getStockBlockInfo() {
        String url =stockInfoConfig.getBlockUrl();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        if (statusCodeValue != 200) {
            log.error("采集板块数据失败,状态码：{}", statusCodeValue);
        }
        String jsData = responseEntity.getBody();
        List<StockBlockRtInfo> entities = parserStockInfoUtil.parse4StockBlock(jsData);
        /*log.info("采集的当前板块数据：{}", entities);
        int count = stockBlockRtInfoMapper.insertBatch(entities);
        if (count > 0) {
            log.info("插入板块数据成功！批量插入了：{}条数据", count);
        } else {
            log.info("插入板块数据失败！");
        }*/
        /*Lists.partition(entities,20).forEach(list->{
            threadPoolTaskExecutor.execute(()->{
                log.info("采集的当前板块数据：{}", list);
                int count = stockBlockRtInfoMapper.insertBatch(list);
                if (count > 0) {
                    log.info("插入板块数据成功！批量插入了：{}条数据", count);
                } else {
                    log.info("插入板块数据失败！");
                }
            });
        });*/
        Lists.partition(entities,20).forEach(list->{
            Map<String, Object> taskMap = new HashMap<>();
            taskMap.put("data",list);
            threadPoolTaskExecutor.execute(new StockTaskRunable(taskMap) {
                @Override
                public void run() {
                    log.info("采集的当前板块数据：{}", list);
                    int count = stockBlockRtInfoMapper.insertBatch(list);
                    if (count > 0) {
                        log.info("插入板块数据成功！批量插入了：{}条数据", count);
                    } else {
                        log.info("插入板块数据失败！");
                    }
                }
            });
        });
    }

    @PostConstruct
    public void initHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        httpEntity = new HttpEntity<>(headers);
    }
}
