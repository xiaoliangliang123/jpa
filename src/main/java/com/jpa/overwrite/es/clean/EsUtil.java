package com.jpa.overwrite.es.clean;


import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.stats.IndexStats;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;

import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.hsqldb.persist.HsqlProperties.indexName;

public class EsUtil {

    public final static String INDEX_WK_TOPIC = "wk_topic";
    public final static String HOST = "127.0.0.1";
    public final static int PORT = 9200;
    public final static String PROTOCOL = "http";
    private static EsUtil esUtil = null;

    private EsUtil() {
    }

    public static EsUtil instance() {
        if (esUtil == null) {
            esUtil = new EsUtil();
        }
        return esUtil;
    }

    public static String findForums(String serchKey) {
        String[] keys = serchKey.split("&&nbsp");
        List<String> skeys = new ArrayList<>(Arrays.asList(keys));
        skeys.remove(keys[keys.length - 1]);
        return skeys.toString();
    }

    public static String findKeyword(String serchKey) {
        String[] keys = serchKey.split("&&nbsp");
        return keys[keys.length - 1];
    }



    public void deleteByQueryRequest() throws IOException {


        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("");
        boolean isAcknowledged = getRestClient().indices().delete(deleteIndexRequest).isAcknowledged();
    }

    public RestHighLevelClient getRestClient() {

        RestClientBuilder builder = RestClient.builder(new HttpHost(HOST, PORT, PROTOCOL));
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);
        return restHighLevelClient;
    }




}
