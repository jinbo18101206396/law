package cn.stylefeng.guns.modular.controller;

import cn.stylefeng.guns.modular.model.request.QueryRequest;
import cn.stylefeng.guns.modular.service.QueryService;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 原告/被告/反诉原告/反诉被告质证控制器
 *
 * @author 金波
 * @date 2022/01/21
 */
@RestController
@ApiResource(name = "质证")
public class QueryController {

    @Resource
    private QueryService queryService;

    /**
     * 保存质证信息
     *
     * @author 金波
     * @date 2022/01/21
     */
    @PostResource(name = "保存质证信息", path = "/query/add")
    public ResponseData add(@RequestBody @Validated(QueryRequest.add.class) QueryRequest queryRequest) {
        queryService.add(queryRequest);
        return new SuccessResponseData();
    }

    /**
     * 通过ID更新质证信息
     *
     * @author 金波
     * @date 2022/01/21
     */
    @PostResource(name = "通过ID更新质证信息", path = "/query/edit")
    public ResponseData editById(@RequestBody @Validated(QueryRequest.edit.class) QueryRequest queryRequest) {
        queryService.updateById(queryRequest);
        return new SuccessResponseData();
    }

    /**
     * 通过案号和名称更新质证信息
     *
     * @author 金波
     * @date 2022/01/21
     */
    @PostResource(name = "通过案号和名称更新质证信息", path = "/query/edit/number/name")
    public ResponseData editByNumberAndName(@RequestBody @Validated(QueryRequest.edit.class) QueryRequest queryRequest) {
        queryService.updateByNumberAndName(queryRequest);
        return new SuccessResponseData();
    }

}
