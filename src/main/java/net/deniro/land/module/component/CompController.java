package net.deniro.land.module.component;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前端组件
 *
 * @author deniro
 *         2015/10/28
 */
@Controller
@RequestMapping("/comp")
public class CompController {

    /**
     * 跳转至 单位与部门查找带回组件
     *
     * @return
     */
    @RequestMapping(value = "/lookupCompanyDepartment")
    public String index() {
        return "/component/lookup-company-department";
    }

}
