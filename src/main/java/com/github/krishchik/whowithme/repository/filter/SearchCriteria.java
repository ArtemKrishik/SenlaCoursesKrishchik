package com.github.krishchik.whowithme.repository.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
    private Boolean orPredicate;



}