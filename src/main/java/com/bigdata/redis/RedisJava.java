package com.bigdata.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by charles on 2017/7/2.
 */
public class RedisJava {
    static String constr = "192.168.64.131" ;
    public static Jedis getRedis(){
        Jedis jedis = new Jedis(constr) ;
        jedis.auth("Aa123456");
        return jedis ;
    }
    public static void main(String[] args){
        Jedis j = RedisJava. getRedis() ;
        String output ;
        j.set( "hello", "world" ) ;
        output = j.get( "hello") ;
        System. out.println(output) ;
    }
}
