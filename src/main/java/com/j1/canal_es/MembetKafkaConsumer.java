package com.j1.canal_es;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
public class MembetKafkaConsumer {

    @KafkaListener(topics = "myexample")
    public static void receive(ConsumerRecord<?, ?> consumer) {
        System.out.println("topic名称:" + consumer.topic() + ",key:" +
                consumer.key() + "," +
                "分区位置:" + consumer.partition()
                + ", 下标" + consumer.offset() + "," + consumer.value());
        String json = (String) consumer.value();
        JSONObject jsonObject = JSONObject.parseObject(json);
        String type = jsonObject.getString("type");

        String pkNames = jsonObject.getJSONArray("pkNames").getString(0);
        JSONArray data = jsonObject.getJSONArray("data");
        String table = jsonObject.getString("table");
        String database = jsonObject.getString("database");
        for (int i = 0; i < data.size(); i++) {
            JSONObject dataObject = data.getJSONObject(i);
            // 分割key名称.
            String key = database + ":" + table + ":" + dataObject.getString(pkNames);
            switch (type) {
                case "UPDATE":
                case "INSERT":
                    System.out.println(type);
                    break;
                case "DELETE":
                    System.out.println(type);
                    break;
                default:
                    break;
            }
        }

    }
}

