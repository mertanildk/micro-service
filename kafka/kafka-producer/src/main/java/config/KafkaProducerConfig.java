package config;


import com.microservices.demo.config.KafkaConfigData;
import com.microservices.demo.config.KafkaProducerConfigData;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig<K extends Serializable, V extends SpecificRecordBase> { //SpecificRecordBase is a class from avro
    private KafkaConfigData kafkaConfigData;
    private KafkaProducerConfigData kafkaProducerConfigData;

    @Bean
    public Map<String, Object> producerConfigs() {//producer özellikleri
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers());//kafka sunucusu adresleri ve port numaraları
        props.put(kafkaConfigData.getSchemaRegistryUrlKey(), kafkaConfigData.getSchemaRegistryUrl());//schema registry adresi
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerConfigData.getValueSerializerClass());//kafka mesajı için serializer
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProducerConfigData.getBatchSizeBoostFactor());// bant genişliğini ayarlamak için kullanılır
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerConfigData.getLingerMs());// linger ms, bir mesajın gönderilmesi için beklenen süredir
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaProducerConfigData.getCompressionType());//kafka mesajı için sıkıştırma türü
        props.put(ProducerConfig.ACKS_CONFIG,kafkaProducerConfigData.getAcks());// mesajın gönderilmesi için beklenen cevap
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,kafkaProducerConfigData.getRequestTimeoutMs()); //kafka sunucusuna mesaj gönderme zaman aşımı
        props.put(ProducerConfig.RETRIES_CONFIG,kafkaProducerConfigData.getRetryCount());//kafka sunucusuna mesaj gönderme tekrar sayısı
        return props;
    }

    @Bean
    public ProducerFactory<K, V> producerFactory() {//bir kafka üreticisi oluşturur
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean //KafkaTemplate yüksek seviyeli bir API'dir, Kafka'ya mesaj göndermek için kullanılır.
    public KafkaTemplate<K, V> kafkaTemplate() {//bir kafka şablonu oluşturur
        return new KafkaTemplate<>(producerFactory());
    }

}
