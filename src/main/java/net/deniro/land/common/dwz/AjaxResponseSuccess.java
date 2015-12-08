package net.deniro.land.common.dwz;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Ajax 响应（正常状态）
 *
 * @author deniro
 *         15-3-27下午5:52
 */
@Data
public class AjaxResponseSuccess extends AjaxResponse {

    /**
     * 上传的文件数量
     */
    private Integer uploadFileCount;

    /**
     * 上传的文件路径列表
     */
    private List<String> uploadFilePaths = new ArrayList<String>();

    public AjaxResponseSuccess(String message) {
        super(200, message);
    }
}
