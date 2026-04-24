package com.stone.manage.domain.VO;

import com.stone.manage.domain.Task;
import com.stone.manage.domain.TaskType;
import lombok.Data;

@Data
public class TaskVO extends Task{

    private TaskType taskType;
}
