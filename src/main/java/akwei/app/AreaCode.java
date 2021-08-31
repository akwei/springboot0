package akwei.app;


import lombok.Data;

import java.util.Date;

@Data
public class AreaCode {
    private Long recId;

    private String areaCode;

    private String area;

    private Integer level;

    private String comMemo;

    private Date createDate;

    private Long parentAreaId;

}