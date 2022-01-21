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
import cn.stylefeng.guns.modular.model.request.DefendantRequest;
import cn.stylefeng.guns.modular.service.DefendantService;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 被告信息控制器
 *
 * @author 金波
 * @date 2022/01/20
 */
@RestController
@ApiResource(name = "被告信息")
public class DefendantController {

    @Resource
    private DefendantService defendantService;

    /**
     * 保存被告信息
     *
     * @author 金波
     * @date 2022/01/20
     */
    @PostResource(name = "保存被告信息", path = "/defendant/add")
    public ResponseData add(@RequestBody @Validated(DefendantRequest.add.class) DefendantRequest defendantRequest) {
        defendantService.add(defendantRequest);
        return new SuccessResponseData();
    }

    /**
     * 通过ID编辑被告信息
     *
     * @author 金波
     * @date 2022/01/20
     */
    @PostResource(name = "通过ID编辑被告信息", path = "/defendant/edit")
    public ResponseData editById(@RequestBody @Validated(DefendantRequest.edit.class) DefendantRequest defendantRequest) {
        defendantService.updateById(defendantRequest);
        return new SuccessResponseData();
    }

    /**
     * 通过案号和被告类型编辑被告信息
     *
     * @author 金波
     * @date 2022/01/20
     */
    @PostResource(name = "通过案号和被告类型编辑被告信息", path = "/defendant/edit/number/type")
    public ResponseData updateByNumberAndType(@RequestBody @Validated(DefendantRequest.edit.class) DefendantRequest defendantRequest) {
        defendantService.updateByNumberAndType(defendantRequest);
        return new SuccessResponseData();
    }

    /**
     * 通过被告全称和案号更新权利告知信息
     *
     * @author 金波
     * @date 2022/01/20
     */
    @PostResource(name = "更新被告权利告知信息", path = "/defendant/right/duty")
    public ResponseData updateRightDutyByDefendantAndNumber(@RequestBody @Validated(DefendantRequest.edit.class) DefendantRequest defendantRequest) {
        defendantService.updateRightDutyByDefendantAndNumber(defendantRequest);
        return new SuccessResponseData();
    }

    /**
     * 通过被告全称和案号更新是否申请回避信息
     *
     * @author 金波
     * @date 2022/01/20
     */
    @PostResource(name = "更新被告是否回避信息", path = "/defendant/avoid")
    public ResponseData updateAvoidByDefendantAndNumber(@RequestBody @Validated(DefendantRequest.edit.class) DefendantRequest defendantRequest) {
        defendantService.updateAvoidByDefendantAndNumber(defendantRequest);
        return new SuccessResponseData();
    }

    /**
     * 通过被告全称和案号更新最后陈述意见
     *
     * @author 金波
     * @date 2022/01/21
     */
    @PostResource(name = "更新被告最后陈述意见", path = "/defendant/statement")
    public ResponseData updateStatementByDefendantAndNumber(@RequestBody @Validated(DefendantRequest.edit.class) DefendantRequest defendantRequest) {
        defendantService.updateStatementByDefendantAndNumber(defendantRequest);
        return new SuccessResponseData();
    }

    /**
     * 通过被告全称和案号更新调解方案和庭外和解时间
     *
     * @author 金波
     * @date 2022/01/20
     */
    @PostResource(name = "更新调解方案", path = "/defendant/mediate")
    public ResponseData updateMediateByDefendantAndNumber(@RequestBody @Validated(DefendantRequest.edit.class) DefendantRequest defendantRequest) {
        defendantService.updateMediateByDefendantAndNumber(defendantRequest);
        return new SuccessResponseData();
    }

    /**
     * 通过被告全称和案号更新电子送达裁判文书信息
     *
     * @author 金波
     * @date 2022/01/21
     */
    @PostResource(name = "更新电子送达裁判文书信息", path = "/defendant/delivery")
    public ResponseData updateDeliveryByAccuserAndNumber(@RequestBody @Validated(DefendantRequest.edit.class) DefendantRequest defendantRequest) {
        defendantService.updateDeliveryByAccuserAndNumber(defendantRequest);
        return new SuccessResponseData();
    }
}
