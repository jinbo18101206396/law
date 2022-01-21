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

import cn.stylefeng.guns.modular.model.request.AccuserRequest;
import cn.stylefeng.guns.modular.service.AccuserService;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 原告信息控制器
 *
 * @author 金波
 * @date 2022/01/20
 */
@RestController
@ApiResource(name = "原告信息")
public class AccuserController {

    @Resource
    private AccuserService accuserService;

    /**
     * 保存原告信息
     *
     * @author 金波
     * @date 2022/01/20
     */
    @PostResource(name = "保存原告信息", path = "/accuser/add")
    public ResponseData add(@RequestBody @Validated(AccuserRequest.add.class) AccuserRequest accuserRequest) {
        accuserService.add(accuserRequest);
        return new SuccessResponseData();
    }

    /**
     * 通过ID编辑原告信息
     *
     * @author 金波
     * @date 2022/01/20
     */
    @PostResource(name = "通过ID编辑原告信息", path = "/accuser/edit")
    public ResponseData editById(@RequestBody @Validated(AccuserRequest.edit.class) AccuserRequest accuserRequest) {
        accuserService.updateById(accuserRequest);
        return new SuccessResponseData();
    }

    /**
     * 通过案号和原告类型编辑原告信息
     *
     * @author 金波
     * @date 2022/01/20
     */
    @PostResource(name = "通过案号和类型编辑原告信息", path = "/accuser/edit/number/type")
    public ResponseData updateByNumberAndType(@RequestBody @Validated(AccuserRequest.edit.class) AccuserRequest accuserRequest) {
        accuserService.updateByNumberAndType(accuserRequest);
        return new SuccessResponseData();
    }

    /**
     * 通过原告全称和案号更新权利告知信息
     *
     * @author 金波
     * @date 2022/01/20
     */
    @PostResource(name = "更新原告权利告知信息", path = "/accuser/right/duty")
    public ResponseData updateRightDutyByAccuserAndNumber(@RequestBody @Validated(AccuserRequest.edit.class) AccuserRequest accuserRequest) {
        accuserService.updateRightDutyByAccuserAndNumber(accuserRequest);
        return new SuccessResponseData();
    }

    /**
     * 通过原告全称和案号更新是否申请回避信息
     *
     * @author 金波
     * @date 2022/01/20
     */
    @PostResource(name = "更新原告是否回避信息", path = "/accuser/avoid")
    public ResponseData updateAvoidByAccuserAndNumber(@RequestBody @Validated(AccuserRequest.edit.class) AccuserRequest accuserRequest) {
        accuserService.updateAvoidByAccuserAndNumber(accuserRequest);
        return new SuccessResponseData();
    }

    /**
     * 通过原告全称和案号更新最后陈述意见
     *
     * @author 金波
     * @date 2022/01/20
     */
    @PostResource(name = "更新最后陈述意见", path = "/accuser/statement")
    public ResponseData updateStatementByAccuserAndNumber(@RequestBody @Validated(AccuserRequest.edit.class) AccuserRequest accuserRequest) {
        accuserService.updateStatementByAccuserAndNumber(accuserRequest);
        return new SuccessResponseData();
    }

}
