package com.example.mp_project;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Movie {
    String aplcName;
    String coreHarmRsn;
    String direName;
    String gradeName;
    String leadaName;
    String mvAssoName;
    String oriTitle;
    String prodYear;
    String prodcName;
    String prodcNatnlName;
    String rtCoreHarmRsnNm;
    String rtDate;
    String rtNo;
    String rtStdName1;
    String rtStdName2;
    String rtStdName3;
    String rtStdName4;
    String rtStdName5;
    String rtStdName6;
    String rtStdName7;
    String screTime;
    String stadCont;
    String useTitle;
    String workCont;
}
