package com.smile.controller;

import com.alibaba.fastjson.JSON;
import com.smile.service.ElasticsearchService;
import com.smile.vo.BaseVo;
import com.smile.vo.IndexRequest;
import com.smile.vo.QueryVo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @ClassName IndexController
 * @Author smile
 * @date 2022.10.15 17:23
 */
@Slf4j
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    /**
     * 创建索引
     *
     * @param request
     * @return
     */
    @PostMapping("create")
    public BaseVo<Boolean> createIndex(@RequestBody @Valid IndexRequest request) throws IOException {
        boolean isCreated = elasticsearchService.createIndex(request.getIdxName(), JSON.toJSONString(request.getIndexSql()));
        return BaseVo.success(isCreated);
    }

    /**
     * 异步创建索引
     *
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("createAsync")
    public BaseVo<Boolean> createIndexAsync(@RequestBody @Valid IndexRequest request) throws IOException {
        elasticsearchService.createIndexAsync(request.getIdxName(), JSON.toJSONString(request.getIndexSql()));
        return BaseVo.success(Boolean.TRUE);
    }

    /**
     * 判断索引是否存在
     *
     * @param idxName
     * @return
     */
    @GetMapping("exist/{idxName}")
    public BaseVo<Boolean> indexExist(@PathVariable("idxName") String idxName) throws IOException {
        boolean isExist = elasticsearchService.indexExist(idxName);
        return BaseVo.success(isExist);
    }

    /**
     * 删除索引
     *
     * @param idxName
     * @return
     */
    @GetMapping("delete")
    public BaseVo deleteIndex(String idxName) throws IOException {
        boolean isDelete = elasticsearchService.deleteIndex(idxName);
        return BaseVo.success(isDelete);
    }

    /**
     * 查询
     *
     * @param queryVo
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @PostMapping("query")
    public BaseVo query(@RequestBody @Valid QueryVo queryVo) throws ClassNotFoundException, IOException {
        String className = queryVo.getClassName();
        Class<?> aClass = Class.forName(className);
        Map<String, Object> params = queryVo.getQuery().get("match");
        Set<String> keys = params.keySet();
        MatchQueryBuilder queryBuilder = null;
        for (String key : keys) {
            queryBuilder = QueryBuilders.matchQuery(key, params.get(key));
        }
        if (null != queryBuilder) {
            int pageStart = queryVo.getPageIndex();
            int pageSize = queryVo.getPageSize();
            List<?> data = elasticsearchService.search(queryBuilder, aClass, pageStart, pageSize, new String[]{queryVo.getIdxName()});
            return BaseVo.success(data);
        }
        return BaseVo.success();
    }

}
