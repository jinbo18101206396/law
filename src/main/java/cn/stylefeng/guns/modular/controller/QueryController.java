/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.guns.modular.controller;

import cn.stylefeng.guns.modular.model.request.ProofRequest;
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
    @PostResource(name = "保存质证信息", path = "/proof/add")
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
    @PostResource(name = "通过ID更新质证信息", path = "/proof/edit")
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
    @PostResource(name = "通过案号和名称更新质证信息", path = "/proof/edit/number/name")
    public ResponseData editByNumberAndName(@RequestBody @Validated(QueryRequest.edit.class) QueryRequest queryRequest) {
        queryService.updateByNumberAndName(queryRequest);
        return new SuccessResponseData();
    }

}
