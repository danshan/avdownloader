package com.shanhh.av.service.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * @author dan.shan
 * @since 2014-12-06 3:32 PM
 */
@Data
@NoArgsConstructor
public class MagnetItem implements Serializable {

    private String title;
    private String link;
    /** 文件大小 */
    private String size;
    /** 活跃度 */
    private int hot;

    /** 文件列表 */
    private List<String> fileList;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
