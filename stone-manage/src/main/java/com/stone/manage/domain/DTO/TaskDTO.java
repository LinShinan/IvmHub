package com.stone.manage.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class TaskDTO {

    /** 创建类型 */
    private Long createType;

    /** 售货机编码 */
    private String innerCode;

    /** 执行人ID */
    private Long userId;

    /** 指派人ID */
    private Long assignorId;

    /** 工单类型ID */
    private Long productTypeId;

    /** 备注 */
    private String descri;

    /** 工单详情列表（仅补货工单需要） */
    private List<TaskDetailsDTO> details;
}
