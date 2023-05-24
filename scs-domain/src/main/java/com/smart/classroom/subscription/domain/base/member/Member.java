package com.smart.classroom.subscription.domain.base.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 员工成员
 * @author fusu
 * @date 2023-04-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    /**
     * 工号
     */
    private String workNo;

    /**
     * 花名
     */
    private String nickname;

}
