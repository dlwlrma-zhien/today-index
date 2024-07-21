package com.lcyy.stock.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年07月21日 20:30
 * @Description: TODO:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePermsByRoleIdReqVo {
    private Long id;
    private String name;
    private String description;
    private List<Long> permissionsIds;
}
