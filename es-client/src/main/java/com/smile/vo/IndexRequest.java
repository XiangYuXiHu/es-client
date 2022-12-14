package com.smile.vo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Description
 * @ClassName IndexRequest
 * @Author smile
 * @date 2022.10.15 19:19
 */
@NoArgsConstructor
@AllArgsConstructor
public class IndexRequest {

    private String idxName;
    private IndexSql indexSql;

    public String getIdxName() {
        return idxName;
    }

    public void setIdxName(String idxName) {
        this.idxName = idxName;
    }

    public IndexSql getIndexSql() {
        return indexSql;
    }

    public void setIndexSql(IndexSql indexSql) {
        this.indexSql = indexSql;
    }

    @Override
    public String toString() {
        return "IndexRequest{" +
                "idxName='" + idxName + '\'' +
                ", indexSql=" + indexSql +
                '}';
    }

    /**
     * idxName : idx_location
     * idxSql :
     * {
     *     "dynamic": false,
     *     "properties": {
     *         "location_id": {
     *             "type": "long"
     *         },
     *         "flag": {
     *             "type": "text",
     *             "index": true
     *         },
     *         "local_code": {
     *             "type": "text",
     *             "index": true
     *         },
     *         "local_name": {
     *             "type": "text",
     *             "index": true,
     *             "analyzer": "ik_max_word"
     *         },
     *         "lv": {
     *             "type": "long"
     *         },
     *         "sup_local_code": {
     *             "type": "text",
     *             "index": true
     *         },
     *         "url": {
     *             "type": "text",
     *             "index": true
     *         }
     *     }
     * }
     */
    static class IndexSql {
        private boolean dynamic = false;
        private Map<String, Map<String, Object>> properties;

        public boolean isDynamic() {
            return dynamic;
        }

        public void setDynamic(boolean dynamic) {
            this.dynamic = dynamic;
        }

        public Map<String, Map<String, Object>> getProperties() {
            return properties;
        }

        public void setProperties(Map<String, Map<String, Object>> properties) {
            this.properties = properties;
        }
    }
}
