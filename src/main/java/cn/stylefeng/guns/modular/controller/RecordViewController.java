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

import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import org.springframework.stereotype.Controller;

/**
 * 笔录页面管理
 *
 * @author 金波
 * @date 2022/01/14
 */
@Controller
@ApiResource(name = "笔录的界面渲染")
public class RecordViewController {

    /**
     * 笔录基本信息视图
     *
     * @author 金波
     * @date 2022/01/14
     */
    @GetResource(name = "笔录基本信息视图", path = "/view/record", requiredPermission = false)
    public String recordView() {
        return "/modular/business/record/record.html";
    }

    /**
     *@author liaoweiming
     *@date 2022-06-17 00:20
     */
    @GetResource(name = "点击笔录弹出页面", path = "/view/recordlayer", requiredPermission = false)
    public String recordLayerView() {
        return "/modular/business/record/record_layer.html";
    }

    /**
     * 新增笔录视图
     *
     * @author 金波
     * @date 2022-05-25
     */
    @GetResource(name = "新增笔录视图", path = "/view/record/add", requiredPermission = false)
    public String addRecordView() {
        return "/modular/business/record/record_add.html";
    }

    /**
     * 查看笔录详情视图
     *
     * @author 金波
     * @date 2022-05-25
     */
    @GetResource(name = "查看笔录详情视图", path = "/view/record/detail", requiredPermission = false)
    public String recordDetailView() {

        return "/modular/business/record/record_add.html";
    }
}

