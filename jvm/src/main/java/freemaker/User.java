package freemaker;

import lombok.Data;

@Data
public class User  {
    /**
    * id
    */
    private Long id;

    /**
    * 用户ID
    */
    private String userId;

    /**
    * 姓名
    */
    private String realName;

    /**
    * 年纪
    */
    private Integer age;
}
