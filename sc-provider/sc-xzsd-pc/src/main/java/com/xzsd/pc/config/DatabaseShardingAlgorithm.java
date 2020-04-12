package com.xzsd.pc.config;//package com.config;
//
//import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
//import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
//import com.google.common.collect.Range;
//
//import java.util.Collection;
//import java.util.LinkedHashSet;
//
///**
// * 分库策略类
// */
//public class DatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Integer> {
//
//    @Override
//    public String doEqualSharding(Collection<String> databaseNames, ShardingValue<Integer> shardingValue) {
//        for (String each : databaseNames) {
//            if (each.endsWith(shardingValue.getValue() % 2 + "")) {
//                return each;
//            }
//        }
//        throw new IllegalArgumentException();
//    }
//
//    @Override
//    public Collection<String> doInSharding(Collection<String> databaseNames, ShardingValue<Integer> shardingValue) {
//        Collection<String> result = new LinkedHashSet<>(databaseNames.size());
//        for (Integer value : shardingValue.getValues()) {
//            for (String tableName : databaseNames) {
//                if (tableName.endsWith(value % 2 + "")) {
//                    result.add(tableName);
//                }
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public Collection<String> doBetweenSharding(Collection<String> databaseNames, ShardingValue<Integer> shardingValue) {
//        Collection<String> result = new LinkedHashSet<>(databaseNames.size());
//        Range<Integer> range = shardingValue.getValueRange();
//        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
//            for (String each : databaseNames) {
//                if (each.endsWith(i % 2 + "")) {
//                    result.add(each);
//                }
//            }
//        }
//        return result;
//    }
//
//}