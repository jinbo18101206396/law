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
import cn.stylefeng.guns.modular.model.request.AllegeRequest;
import cn.stylefeng.guns.modular.service.*;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 法庭调查控制器
 *
 * @author 金波
 * @date 2022/01/20
 */
@RestController
@ApiResource(name = "法庭调查")
public class InvestigateController {

    @Resource
    private AllegeService allegeService;

    @Resource
    private ReplyService replyService;

    @Resource
    private ProofService proofService;

    @Resource
    private QueryService queryService;

    @Resource
    private InquiryService inquiryService;

    /**
     * 保存诉讼请求和事实理由
     *
     * @author 金波
     * @date 2022/01/20
     */
    @PostResource(name = "诉讼请求和事实理由", path = "/allege/add")
    public ResponseData add(@RequestBody @Validated(AllegeRequest.add.class) AllegeRequest allegeRequest) {
        allegeService.add(allegeRequest);
        return new SuccessResponseData();
    }


}
