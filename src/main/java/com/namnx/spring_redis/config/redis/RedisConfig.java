package com.namnx.spring_redis.config.redis;

import com.namnx.spring_redis.enums.pub_sub.TypeChannel;
import com.namnx.spring_redis.enums.pub_sub.TypeMessageListener;
import com.namnx.spring_redis.service.pub_sub.MessageReceiver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
    }

    @Bean(name = "redisTemplate")
    public <E> RedisTemplate<String, E> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, E> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean(name = "systemAdapter")
    public MessageListenerAdapter systemAdapter(MessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, TypeMessageListener.SYSTEM.getNameOfMethodListener());
    }

    @Bean(name = "clientAdapter")
    public MessageListenerAdapter clientAdapter(MessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, TypeMessageListener.CLIENT.getNameOfMethodListener());
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter systemAdapter,
                                                   MessageListenerAdapter clientAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //Specify different ways to listen to different channels
        container.addMessageListener(systemAdapter, new PatternTopic(TypeChannel.SYSTEM.name()));
        container.addMessageListener(clientAdapter, new PatternTopic(TypeChannel.CLIENT.name()));
        return container;
    }
}
